package com.oga.app.service.businesslogic.redstone.campaign;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.oga.app.common.enums.LoginCampaignType;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.entity.LoginCampaignDetails;
import com.oga.app.dataaccess.entity.LoginCampaignHistory;
import com.oga.app.dataaccess.entity.RSManagement;
import com.oga.app.master.provider.MasterRepositoryProvider;
import com.oga.app.service.beans.LoginCampaignServiceBean;
import com.oga.app.service.businesslogic.BusinessLogicBase;
import com.oga.app.service.provider.redstone.campaign.RedstoneLoginCampaignProvider;
import com.oga.app.service.response.LoginResult;
import com.oga.app.service.response.redstoneapi.LoginCampaignResponse;
import com.oga.app.service.response.redstoneapi.State;

/**
 * ログインキャンペーンビジネスロジック
 */
public class RedStoneLoginCampaignBusinessLogic extends BusinessLogicBase<LoginCampaignServiceBean> {

	/** ログインキャンペーンビジネスロジック */
	private static RedStoneLoginCampaignBusinessLogic redstoneLoginBusinessLogic;

	/** ログインキャンペーンプロバイダ */
	private RedstoneLoginCampaignProvider redstoneLoginCampaignProvider;

	/** マスタ情報プロバイダ */
	private MasterRepositoryProvider masterRepositoryProvider;

	/** LoginCampaignServiceBean */
	private LoginCampaignServiceBean loginCampaignServiceBean;

	/** ログインキャンペーンのチャプタ1のステージ数 */
	private final String CHAPTER_STAGE_1 = "7";

	/** ログインキャンペーンのチャプタ2のステージ数 */
	private final String CHAPTER_STAGE_2 = "14";

	/** ログインキャンペーンのチャプタ3のステージ数 */
	private final String CHAPTER_STAGE_3 = "21";

	private final Map<String, LoginCampaignType> STAGE_TO_LOGINCAMPAIGN_TYPE = Map.of(
			CHAPTER_STAGE_1, LoginCampaignType.CMPLETE_CHAPTER1,
			CHAPTER_STAGE_2, LoginCampaignType.CMPLETE_CHAPTER2,
			CHAPTER_STAGE_3, LoginCampaignType.CMPLETE_CHAPTER3);

	/**
	 * コンストラクタ
	 * 
	 * @param driver WebDriver
	 */
	public RedStoneLoginCampaignBusinessLogic() {
		this.redstoneLoginCampaignProvider = RedstoneLoginCampaignProvider.getInstance();
		this.masterRepositoryProvider = MasterRepositoryProvider.getInstance();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedStoneLoginCampaignBusinessLogic getInstance() {
		if (redstoneLoginBusinessLogic == null) {
			redstoneLoginBusinessLogic = new RedStoneLoginCampaignBusinessLogic();
		}
		return redstoneLoginBusinessLogic;
	}

	@Override
	protected void createServiceBean(LoginCampaignServiceBean serviceBean) {
		this.loginCampaignServiceBean = serviceBean;

		// 基準日を設定する
		this.loginCampaignServiceBean.setBaseDate(DateUtil.getBaseDate());
	}

	/**
	 * データ取得およびチェック処理
	 * <pre>
	 * 以下のデータ取得およびチェック処理を行う。
	 * ・ログイン状態チェック
	 * ・認証情報チェック
	 * </pre>
	 * @throws SystemException 
	 */
	@Override
	protected void checkSelectData() throws ApplicationException, SystemException {
		// ログイン状態チェック
		if (!loginCampaignServiceBean.isLogged()) {
			throw new ApplicationException("レッドストーンにログインしていないため処理を終了します。");
		}

		// 認証情報チェック
		if (loginCampaignServiceBean.getLoginResult() == null) {
			throw new ApplicationException("認証情報が設定されていないため処理を終了します。");
		}
	}

	/**
	 * ログインキャンペーン処理
	 * <pre>
	 * 以下の業務処理を行う。
	 * ・現在のログインキャンペーン詳細情報を取得する
	 * ・ログインキャンペーン情報照会APIを実行して現在の情報を取得する
	 * ・ログインキャンペーンを実施するか判定する
	 * ・ログインキャンペーンAPIを実行する
	 * ・ログインキャンペーン情報を更新する
	 * ・ログインキャンペーン履歴を登録する
	 * ・ログインキャンペーンコンプリートマスAPIを実行する
	 * ・レッドストーン管理を更新する
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {
		LogUtil.info("[ログインキャンペーン処理] [START]");

		// レッドストーン管理情報を取得する
		RSManagement rsManagement = fetchRSManagement(loginCampaignServiceBean.getUserId());

		// ログインキャンペーン詳細情報を取得する
		LoginCampaignDetails currentLoginCampaignDetails = fetchLoginCampaignDetails(DateUtil.getBaseMonth());

		// ログインキャンペーンを実施するべきか判定する
		if (!shouldExecuteLoginCampaign(rsManagement, currentLoginCampaignDetails, loginCampaignServiceBean.getBaseDateSql())) {
			return;
		}

		LogUtil.info("[ログインキャンペーン処理] [通常マス] [START]");

		// ログインキャンペーン情報照会API
		State currentState = performLoginCampaignState();

		// ログインキャンペーンAPI(通常マス)
		LoginCampaignDetails loginCampaignDetails = handleLoginCampaignNormalStage(currentState);

		// ログインキャンペーン詳細情報を更新する
		updateLoginCampaignDetails(loginCampaignDetails);

		// ログインキャンペーン履歴を構築する
		LoginCampaignHistory loginCampaignHistory = buildLoginCampaignHistory(loginCampaignDetails, LoginCampaignType.NORMAL);

		// ログインキャンペーン履歴を登録する
		insertLoginCampaignHistory(loginCampaignHistory);

		LogUtil.info("[ログインキャンペーン処理] [通常マス] [" + loginCampaignDetails.getStage() + "日目]");
		LogUtil.info("[ログインキャンペーン処理] [通常マス] [獲得アイテム："
				+ masterRepositoryProvider.getItemName(loginCampaignHistory.getRewardItem1()) + "]");
		LogUtil.info("[ログインキャンペーン処理] [通常マス] [END]");

		LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [START]");

		// ログインキャンペーンAPI(コンプリートマス)
		handleLoginCampaignChapterCompletionIfNeeded(loginCampaignDetails.getStage());

		LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [END]");

		// レッドストーン管理を更新する
		updateRSManagement();

		LogUtil.info("[ログインキャンペーン処理] [END]");
	}

	/**
	 * レッドストーン管理情報を取得する
	 * 
	 * @param userId ユーザID
	 * @return
	 */
	private RSManagement fetchRSManagement(String userId) {
		return redstoneLoginCampaignProvider.fetchRSManagement(userId);
	}

	/**
	 * ログインキャンペーン詳細情報を取得する
	 * 
	 * @param targetMonth 対象月
	 * @return
	 */
	private LoginCampaignDetails fetchLoginCampaignDetails(String targetMonth) {
		return redstoneLoginCampaignProvider.fetchLoginCampaignDetails(loginCampaignServiceBean.getUserId(), targetMonth);
	}

	/**
	 * 現在のログインキャンペーン状態を取得する
	 * 
	 * @return ログインキャンペーン状態
	 * @throws SystemException API通信エラーが発生した場合
	 */
	private State performLoginCampaignState() throws SystemException {
		return redstoneLoginCampaignProvider.performLoginCampaignStateApi(loginCampaignServiceBean.getLoginResult());
	}

	/**
	 * ログインキャンペーンを実施するべきか判定する
	 * 
	 * @param rsManagement レッドストーン管理情報
	 * @param loginCampaignDetails ログインキャンペーン詳細情報
	 * @param baseDate 基準日(yyyy-MM-dd形式)
	 * @return
	 */
	private boolean shouldExecuteLoginCampaign(RSManagement rsManagement, LoginCampaignDetails loginCampaignDetails, String baseDate) {
		// ログインキャンペーンフラグが「N」の場合はスキップする
		if (YesNo.NO.getValue().equals(rsManagement.getLoginCampaignFlg())) {
			LogUtil.info("ログインキャンペーンフラグが「N」の場合はスキップします。");
			return false;
		}

		// 既に本日分のログインキャンペーンを実施済みの場合はスキップする
		if (null != loginCampaignDetails && baseDate.equals(rsManagement.getLastLoginCampaignDate())) {
			LogUtil.info("本日のログインキャンペーンは実施済みのためスキップします。");
			return false;
		}

		// 現在の進行度を確認して21日目まで達成している場合はスキップする
		if (null != loginCampaignDetails && Integer.parseInt(loginCampaignDetails.getStage()) >= 21) {
			LogUtil.info("当月分のログインキャンペーンをすべて実施済みの場合はスキップします。");
			return false;
		}

		return true;
	}

	/**
	 * 通常のログインキャンペーンAPIを実行し、結果に応じたログインキャンペーン詳細情報を生成する。
	 *
	 * <pre>
	 * APIが成功した場合はレスポンス内の状態情報からログインキャンペーン詳細情報を生成し、
	 * エラーが発生した場合は既存のログインキャンペーン状態を利用してログインキャンペーン詳細情報を生成して返却する。
	 * </pre>
	 *
	 * @param currentState 現在のログインキャンペーン状態
	 * @return 通常マスの実施結果に基づくログインキャンペーン詳細情報
	 * @throws SystemException API通信エラーが発生した場合
	 * @throws ApplicationException 想定外のエラー応答が発生した場合
	 */

	private LoginCampaignDetails handleLoginCampaignNormalStage(State currentState) throws SystemException, ApplicationException {
		// ログインキャンペーンAPI
		LoginCampaignResponse response = loginCampaign(loginCampaignServiceBean.getLoginResult(), LoginCampaignType.NORMAL);

		if (response.getErrorInfo() == null) {
			return buildLoginCampaignDetails(loginCampaignServiceBean.getUserId(), response.getLoginCampaignData().getState());

		} else if ("DONE".equals(response.getErrorInfo().getMessage())) {
			// エラーメッセージが「DONE」の場合は既にログインキャンペーンを実施済みとなる
			// バッチでの実行ではなく、手動で実行したと見られるため現在のログインキャンペーン状態を返却する
			return buildLoginCampaignDetails(loginCampaignServiceBean.getUserId(), currentState);

		} else {
			throw new ApplicationException("ログインキャンペーンAPIでエラーが発生しました：" +
					response.getErrorInfo().getMessage());
		}
	}

	/**
	 * ログインキャンペーン詳細情報を元にチャプター完了条件を満たしている場合は
	 * コンプリートマスの実行とログインキャンペーン詳細情報の更新処理を行う。
	 *
	 * <pre>
	 * 対象ステージがチャプター完了定義に含まれている場合、
	 * 指定されたログインキャンペーン種別でAPIを実行し、成功した場合はレスポンス情報から
	 * ログインキャンペーン詳細情報を構築・更新する。エラーが発生した場合は業務例外をスローする。
	 * </pre>
	 *
	 * @param stage 現在のステージ数
	 * @throws SystemException API通信エラーが発生した場合
	 * @throws ApplicationException 想定外のエラー応答が発生した場合
	 */
	private void handleLoginCampaignChapterCompletionIfNeeded(String stage)
			throws SystemException, ApplicationException {
		// 各チャプタのステージ数に達した場合はコンプリート処理を行う
		LoginCampaignType loginCampaignType = STAGE_TO_LOGINCAMPAIGN_TYPE.get(stage);

		if (loginCampaignType == null) {
			LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [未達成]");
			return;
		}

		// ログインキャンペーンコンプリートマス
		LoginCampaignResponse response = loginCampaign(loginCampaignServiceBean.getLoginResult(), loginCampaignType);

		if (response.getErrorInfo() == null) {
			// ログインキャンペーン詳細情報を構築する
			LoginCampaignDetails completeLoginCampaignDetails = buildLoginCampaignDetails(loginCampaignServiceBean.getUserId(),
					response.getLoginCampaignData().getState());
			// ログインキャンペーン詳細情報を更新する
			updateLoginCampaignDetails(completeLoginCampaignDetails);

			// ログインキャンペーン履歴を構築する
			LoginCampaignHistory loginCampaignHistory = buildLoginCampaignHistory(completeLoginCampaignDetails, loginCampaignType);
			// ログインキャンペーン履歴を登録する
			insertLoginCampaignHistory(loginCampaignHistory);

			LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [獲得アイテム："
					+ masterRepositoryProvider.getItemName(loginCampaignHistory.getRewardItem1()) + "]");
			LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [獲得アイテム："
					+ masterRepositoryProvider.getItemName(loginCampaignHistory.getRewardItem2()) + "]");
		} else if ("DONE".equals(response.getErrorInfo().getMessage())) {
			LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [既に実施済み]");
			return;
		} else {
			throw new ApplicationException("ログインキャンペーンAPIでエラーが発生しました：" + response.getErrorInfo().getMessage());
		}

	}

	/**
	 * ログインキャンペーンAPIを実行してレスポンスを返却する
	 * 
	 * @param loginResult 認証情報
	 * @param loginCampaignType 実行するログインキャンペーン種別
	 * @return API実行結果
	 * @throws SystemException API通信に失敗した場合など、システムレベルの例外
	 */
	private LoginCampaignResponse loginCampaign(LoginResult loginResult, LoginCampaignType loginCampaignType) throws SystemException {
		return redstoneLoginCampaignProvider.performLoginCampaignApi(loginResult, loginCampaignType);
	}

	/**
	 * ログインキャンペーン詳細情報を登録または更新する
	 * 
	 * @param loginCampaignDetails ログインキャンペーン詳細情報
	 */
	private void updateLoginCampaignDetails(LoginCampaignDetails loginCampaignDetails) {
		redstoneLoginCampaignProvider.updateLoginCampaignDetails(loginCampaignDetails);
	}

	/**
	 * ログインキャンペーン履歴を登録または更新する
	 * 
	 * @param loginCampaignDetails ログインキャンペーン詳細情報
	 */
	private void insertLoginCampaignHistory(LoginCampaignHistory loginCampaignHistory) {
		redstoneLoginCampaignProvider.insertLoginCampaignHistory(loginCampaignHistory);
	}

	private void updateRSManagement() {
		RSManagement rsManagement = RSManagement.builder()
				.userId(loginCampaignServiceBean.getUserId())
				.lastLoginCampaignDate(loginCampaignServiceBean.getBaseDateSql())
				.build();
		redstoneLoginCampaignProvider.updateRSManagement(rsManagement);
	}

	/**
	 * ログインキャンペーンAPIのレスポンス情報からログインキャンペーン詳細情報を構築する
	 * 
	 * @param userId ユーザID
	 * @param state API実行結果(ログインキャンペーン状態)
	 * @return ログインキャンペーン詳細情報
	 */
	private LoginCampaignDetails buildLoginCampaignDetails(String userId, State state) {
		return LoginCampaignDetails.builder()
				.userId(userId)
				.targetMonth(state.getTargetMonth())
				.stage(state.getStage())
				.completeItemChapter1(state.getCompleteItemChapter1())
				.completeItemChapter2(state.getCompleteItemChapter2())
				.completeItemChapter3(state.getCompleteItemChapter3())
				.rewardItems(state.getRewardItems())
				.deleteFlg(YesNo.NO.getValue())
				.build();
	}

	/**
	 * ログインキャンペーン詳細情報からログインキャンペーン履歴を構築する
	 * 
	 * @param loginCampaignDetails ログインキャンペーン詳細情報
	 * @param state API実行結果(ログインキャンペーン状態)
	 * @return ログインキャンペーン履歴
	 */
	private LoginCampaignHistory buildLoginCampaignHistory(LoginCampaignDetails loginCampaignDetails, LoginCampaignType loginCampaignType) {
		List<String> rewardItemList = null;
		String rewardItem1 = null;
		String rewardItem2 = null;

		switch (loginCampaignType) {
		case LoginCampaignType.NORMAL: {
			rewardItemList = Arrays.asList(loginCampaignDetails.getRewardItems().split(","));
			rewardItem1 = rewardItemList.get(rewardItemList.size() - 1);
			break;
		}
		case LoginCampaignType.CMPLETE_CHAPTER1: {
			rewardItemList = Arrays.asList(loginCampaignDetails.getCompleteItemChapter1().split(":"));
			rewardItem1 = rewardItemList.get(0);
			rewardItem2 = rewardItemList.get(1);
			break;
		}
		case LoginCampaignType.CMPLETE_CHAPTER2: {
			rewardItemList = Arrays.asList(loginCampaignDetails.getCompleteItemChapter2().split(":"));
			rewardItem1 = rewardItemList.get(0);
			rewardItem2 = rewardItemList.get(1);
			break;
		}
		case LoginCampaignType.CMPLETE_CHAPTER3: {
			rewardItemList = Arrays.asList(loginCampaignDetails.getCompleteItemChapter3().split(":"));
			rewardItem1 = rewardItemList.get(0);
			rewardItem2 = rewardItemList.get(1);
			break;
		}
		}

		return LoginCampaignHistory.builder()
				.userId(loginCampaignDetails.getUserId())
				.targetMonth(loginCampaignDetails.getTargetMonth())
				.targetDate(loginCampaignServiceBean.getBaseDateSql())
				.stage(loginCampaignDetails.getStage())
				.loginCampaignType(loginCampaignType.getValue())
				.rewardItem1(rewardItem1)
				.rewardItem2(rewardItem2)
				.deleteFlg(YesNo.NO.getValue())
				.build();
	}
}
