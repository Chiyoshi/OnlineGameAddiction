//package com.oga.app.batch;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import com.oga.app.common.enums.ServiceType;
//import com.oga.app.common.exception.ApplicationException;
//import com.oga.app.common.exception.SystemException;
//import com.oga.app.common.prop.OgaProperty;
//import com.oga.app.common.utils.FileUtil;
//import com.oga.app.common.utils.LogUtil;
//import com.oga.app.common.utils.StringUtil;
//import com.oga.app.dataaccess.entity.Campaign;
//import com.oga.app.service.provider.CampaignProvider;
//import com.oga.app.service.provider.DailyWorkResultProvider;
//
//public class OutputRewardItemAggregateBatch extends BaseBatch {
//
//	/** CSV格納先ディレクトリ */
//	private String OTUPUT_CSV_PATH = null;
//
//	/** キャンペーン情報 */
//	private Campaign campaign;
//
//	@Override
//	public void pre(String[] args) throws ApplicationException, SystemException {
//
//		// 引数チェック
//		if (args.length < 1) {
//			throw new ApplicationException("引数が設定されていません。：キャンペーンID");
//		}
//
//		// キャンペーンIDを設定する
//		String campaignId = args[0];
//
//		// 環境変数からパスを取得する
//		String outputCsvFolder = System.getProperty("output.dir");
//
//		if (StringUtil.isNullOrEmpty(outputCsvFolder)) {
//			throw new SystemException("環境変数が設定されていません。：output.dir");
//		}
//
//		// ディレクトリ存在チェック
//		if (!FileUtil.isExists(outputCsvFolder)) {
//			throw new ApplicationException("CSV出力先のフォルダが存在しません。：" + outputCsvFolder);
//		}
//
//		//		// ルーレットのディレクトリが存在しない場合は作成する
//		//		outputCsvPath = outputCsvPath + "\\roulette";
//		//
//		//		if (!FileUtil.isExists(outputCsvPath)) {
//		//			FileUtil.createDirectory(outputCsvPath);
//		//		}
//
//		// キャンペーン情報を取得する
//		this.campaign = CampaignProvider.getInstance().getCampaign(campaignId);
//
//		// キャンペーン情報が登録されていない場合はエラーとする
//		if (this.campaign == null) {
//			throw new ApplicationException("対象のキャンペーン情報が登録されていません");
//		}
//
//		// CSVファイルパス
//		this.OTUPUT_CSV_PATH = outputCsvFolder + "\\" + this.campaign.getCampaignId() + ".csv";
//	}
//
//	@Override
//	public void exec() throws ApplicationException, SystemException {
//
//		// プロパティファイルから集計対象のアイテム一覧を取得する
//		String items = null;
//
//		// キャンペーン種別が「1：ログインキャンペーン」の場合
//		if (ServiceType.LOGINCAMPAIGN.getValue().equals(campaign.getCampaignType())) {
//			items = OgaProperty.getProperty("redstone.logincampaign.aggregate.items");
//		}
//		// キャンペーン種別が「4：ルーレット」の場合
//		else if (ServiceType.ROULETTE.getValue().equals(campaign.getCampaignType())) {
//			items = OgaProperty.getProperty("redstone.roulette.aggregate.items");
//		}
//		
//		
//		LogUtil.info("[集計対象] [" + items + "]");
//		LogUtil.info("[出力先] [" + this.OTUPUT_CSV_PATH + "]");
//
//		// カンマ区切りで分割する
//		List<String> targetRewardItemList = Arrays.asList(items.split(","));
//
//		// 対象キャンペーンの獲得アイテムの集計結果を取得する
//		List<Object[]> rewardItemAggregateList = DailyWorkResultProvider.getInstance()
//				.getRewardItemAggregateList(this.campaign, targetRewardItemList);
//
//		// ヘッダー
//		List<String> header = new ArrayList<String>();
//		header.addAll(targetRewardItemList);
//		header.add(0, "ユーザID");
//
//		// CSVファイル出力
//		FileUtil.writeCsvFile(this.OTUPUT_CSV_PATH, header.toArray(), rewardItemAggregateList, false);
//
////		// <ユーザID, <アイテム名, 数量>>
////		Map<String, Map<String, Integer>> userItemMap = new LinkedHashMap<>();
////
////		// ユーザーIDごとにアイテムの数量を集計する
////		for (RouletteRewardItemDto rouletteRewardItemDto : rouletteRewardItemList) {
////
////			String userId = rouletteRewardItemDto.getUserId();
////			String itemName = rouletteRewardItemDto.getRewardItem();
////			int quantity = rouletteRewardItemDto.getCount();
////
////			// マップにユーザIDが存在しない場合は格納する
////			if (!userItemMap.containsKey(userId)) {
////				userItemMap.put(userId, new HashMap<String, Integer>());
////			}
////
////			Map<String, Integer> itemMap = userItemMap.get(userId);
////			if (!itemMap.containsKey(itemName)) {
////				itemMap.put(itemName, 0);
////			}
////			itemMap.put(itemName, itemMap.get(itemName) + quantity);
////		}
////
////		// 各ユーザーのデータを出力
////		for (Map.Entry<String, Map<String, Integer>> entry : userItemMap.entrySet()) {
////			Map<String, Integer> itemQuantities = entry.getValue();
////
////			System.out.print("\"" + entry.getKey() + "\"");
////
////			for (String item : targetRewardItemList) {
////				// アイテムが存在しない場合は0
////				int quantity = itemQuantities.containsKey(item) ? itemQuantities.get(item) : 0;
////				System.out.print(",\"" + quantity + "\"");
////			}
////			System.out.println();
////		}
//	}
//
//	@Override
//	public void post() throws ApplicationException, SystemException {
//	}
//
//	public static void main(String[] args) {
//		new OutputRewardItemAggregateBatch().run(args);
//	}
//
//}
