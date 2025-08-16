//package com.oga.app.batch;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.oga.app.batch.base.BatchBase;
//import com.oga.app.common.exception.ApplicationException;
//import com.oga.app.common.exception.SystemException;
//import com.oga.app.common.utils.FileUtil;
//import com.oga.app.common.utils.StringUtil;
//import com.oga.app.dataaccess.dto.RouletteRewardItemDto;
//import com.oga.app.service.provider.DailyWorkResultProvider;
//
//public class OutputRewardItemAggregateBatch extends BatchBase {
//
//	/** CSV格納先ディレクトリ */
//	private String OTUPUT_CSV_PATH = null;
//
//	@Override
//	public void pre(String[] args) throws ApplicationException, SystemException {
//
//		// 環境変数からパスを取得する
//		OTUPUT_CSV_PATH = System.getProperty("output.dir");
//
//		if (StringUtil.isNullOrEmpty(OTUPUT_CSV_PATH)) {
//			throw new SystemException("環境変数が設定されていません。：output.dir");
//		}
//
//		// ディレクトリ存在チェック
//		if (!FileUtil.isExists(OTUPUT_CSV_PATH)) {
//			throw new ApplicationException("CSV出力先のフォルダが存在しません。：" + OTUPUT_CSV_PATH);
//		}
//	}
//
//	@Override
//	public void exec() throws ApplicationException, SystemException {
//
//		// プロパティファイルから集計対象のアイテム一覧を取得する
//		String items = null;
//
////		// キャンペーン種別が「1：ログインキャンペーン」の場合
////		if (ServiceType.LOGINCAMPAIGN.getValue().equals(campaign.getCampaignType())) {
////			items = OgaProperty.getProperty("redstone.logincampaign.aggregate.items");
////		}
////		// キャンペーン種別が「4：ルーレット」の場合
////		else if (ServiceType.ROULETTE.getValue().equals(campaign.getCampaignType())) {
////			items = OgaProperty.getProperty("redstone.roulette.aggregate.items");
////		}
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
//		// <ユーザID, <アイテム名, 数量>>
//		Map<String, Map<String, Integer>> userItemMap = new LinkedHashMap<>();
//
//		// ユーザーIDごとにアイテムの数量を集計する
//		for (RouletteRewardItemDto rouletteRewardItemDto : rouletteRewardItemList) {
//
//			String userId = rouletteRewardItemDto.getUserId();
//			String itemName = rouletteRewardItemDto.getRewardItem();
//			int quantity = rouletteRewardItemDto.getCount();
//
//			// マップにユーザIDが存在しない場合は格納する
//			if (!userItemMap.containsKey(userId)) {
//				userItemMap.put(userId, new HashMap<String, Integer>());
//			}
//
//			Map<String, Integer> itemMap = userItemMap.get(userId);
//			if (!itemMap.containsKey(itemName)) {
//				itemMap.put(itemName, 0);
//			}
//			itemMap.put(itemName, itemMap.get(itemName) + quantity);
//		}
//
//		// 各ユーザーのデータを出力
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
