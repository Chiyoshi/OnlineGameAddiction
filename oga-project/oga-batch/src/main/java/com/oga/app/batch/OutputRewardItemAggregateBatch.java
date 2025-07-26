package com.oga.app.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.prop.OGAProperty;
import com.oga.app.common.utils.FileUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.Campaign;
import com.oga.app.service.provider.CampaignProvider;
import com.oga.app.service.provider.DailyWorkResultProvider;

public class OutputRewardItemAggregateBatch extends BaseBatch {

	/** CSV�i�[��f�B���N�g�� */
	private String OTUPUT_CSV_PATH = null;

	/** �L�����y�[����� */
	private Campaign campaign;

	@Override
	public void pre(String[] args) throws ApplicationException, SystemException {

		// �����`�F�b�N
		if (args.length < 1) {
			throw new ApplicationException("�������ݒ肳��Ă��܂���B�F�L�����y�[��ID");
		}

		// �L�����y�[��ID��ݒ肷��
		String campaignId = args[0];

		// ���ϐ�����p�X���擾����
		String outputCsvFolder = System.getProperty("output.dir");

		if (StringUtil.isNullOrEmpty(outputCsvFolder)) {
			throw new SystemException("���ϐ����ݒ肳��Ă��܂���B�Foutput.dir");
		}

		// �f�B���N�g�����݃`�F�b�N
		if (!FileUtil.isExists(outputCsvFolder)) {
			throw new ApplicationException("CSV�o�͐�̃t�H���_�����݂��܂���B�F" + outputCsvFolder);
		}

		//		// ���[���b�g�̃f�B���N�g�������݂��Ȃ��ꍇ�͍쐬����
		//		outputCsvPath = outputCsvPath + "\\roulette";
		//
		//		if (!FileUtil.isExists(outputCsvPath)) {
		//			FileUtil.createDirectory(outputCsvPath);
		//		}

		// �L�����y�[�������擾����
		this.campaign = CampaignProvider.getInstance().getCampaign(campaignId);

		// �L�����y�[����񂪓o�^����Ă��Ȃ��ꍇ�̓G���[�Ƃ���
		if (this.campaign == null) {
			throw new ApplicationException("�Ώۂ̃L�����y�[����񂪓o�^����Ă��܂���");
		}

		// CSV�t�@�C���p�X
		this.OTUPUT_CSV_PATH = outputCsvFolder + "\\" + this.campaign.getCampaignId() + ".csv";
	}

	@Override
	public void exec() throws ApplicationException, SystemException {

		// �v���p�e�B�t�@�C������W�v�Ώۂ̃A�C�e���ꗗ���擾����
		String items = null;

		// �L�����y�[����ʂ��u1�F���O�C���L�����y�[���v�̏ꍇ
		if (ServiceType.LOGINCAMPAIGN.getValue().equals(campaign.getCampaignType())) {
			items = OGAProperty.getProperty("redstone.logincampaign.aggregate.items");
		}
		// �L�����y�[����ʂ��u4�F���[���b�g�v�̏ꍇ
		else if (ServiceType.ROULETTE.getValue().equals(campaign.getCampaignType())) {
			items = OGAProperty.getProperty("redstone.roulette.aggregate.items");
		}
		
		
		LogUtil.info("[�W�v�Ώ�] [" + items + "]");
		LogUtil.info("[�o�͐�] [" + this.OTUPUT_CSV_PATH + "]");

		// �J���}��؂�ŕ�������
		List<String> targetRewardItemList = Arrays.asList(items.split(","));

		// �ΏۃL�����y�[���̊l���A�C�e���̏W�v���ʂ��擾����
		List<Object[]> rewardItemAggregateList = DailyWorkResultProvider.getInstance()
				.getRewardItemAggregateList(this.campaign, targetRewardItemList);

		// �w�b�_�[
		List<String> header = new ArrayList<String>();
		header.addAll(targetRewardItemList);
		header.add(0, "���[�UID");

		// CSV�t�@�C���o��
		FileUtil.writeCsvFile(this.OTUPUT_CSV_PATH, header.toArray(), rewardItemAggregateList, false);

//		// <���[�UID, <�A�C�e����, ����>>
//		Map<String, Map<String, Integer>> userItemMap = new LinkedHashMap<>();
//
//		// ���[�U�[ID���ƂɃA�C�e���̐��ʂ��W�v����
//		for (RouletteRewardItemDto rouletteRewardItemDto : rouletteRewardItemList) {
//
//			String userId = rouletteRewardItemDto.getUserId();
//			String itemName = rouletteRewardItemDto.getRewardItem();
//			int quantity = rouletteRewardItemDto.getCount();
//
//			// �}�b�v�Ƀ��[�UID�����݂��Ȃ��ꍇ�͊i�[����
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
//		// �e���[�U�[�̃f�[�^���o��
//		for (Map.Entry<String, Map<String, Integer>> entry : userItemMap.entrySet()) {
//			Map<String, Integer> itemQuantities = entry.getValue();
//
//			System.out.print("\"" + entry.getKey() + "\"");
//
//			for (String item : targetRewardItemList) {
//				// �A�C�e�������݂��Ȃ��ꍇ��0
//				int quantity = itemQuantities.containsKey(item) ? itemQuantities.get(item) : 0;
//				System.out.print(",\"" + quantity + "\"");
//			}
//			System.out.println();
//		}
	}

	@Override
	public void post() throws ApplicationException, SystemException {
	}

	public static void main(String[] args) {
		new OutputRewardItemAggregateBatch().run(args);
	}

}
