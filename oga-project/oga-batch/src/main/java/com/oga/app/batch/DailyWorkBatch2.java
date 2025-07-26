package com.oga.app.batch;

import java.util.ArrayList;
import java.util.List;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.Status;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.dao.UserDao;
import com.oga.app.dataaccess.entity.DailyWork;
import com.oga.app.dataaccess.entity.DailyWorkResult;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.businesslogic.redstone.RedStoneLoginCampaignBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedStoneRouletteBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedstoneLoginBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedstoneLogoutBusinessLogic;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.provider.DailyWorkProvider;
import com.oga.app.service.provider.DailyWorkResultProvider;
import com.oga.app.service.provider.UserProvider;
import com.oga.app.service.servicebeans.DailyWorkServiceBean;
import com.oga.app.service.servicebeans.LoginServiceBean;

public class DailyWorkBatch2 extends BaseBatch {

	/** �����Ώ� */
	private List<DailyWork> targetDailyWorkList = new ArrayList<DailyWork>();

	/**
	 * �R���X�g���N�^
	 */
	public DailyWorkBatch2() {
	}

	@Override
	public void pre(String[] args) throws ApplicationException, SystemException {

		// ������擾����
		String baseDate = DateUtil.getBaseDate();

		// ���[�U�����擾����
		List<User> userList = UserProvider.getInstance().getUserList();

		// ������ƌ���
		DailyWorkResult dailyWorkResult = null;

		// ������Ƃ����{���邩�ۂ�
		boolean isExecuted = false;

		// ������Ƃ����{�ς݂̃��[�U�͎��{���Ȃ��悤�����Ώۂ��珜��
		for (User user : userList) {

			// ������Ə����擾����
			DailyWork dailyWork = DailyWorkProvider.getInstance().getDailyWork(user.getUserId());

			if (dailyWork == null) {
				throw new SystemException("������Ə�񂪎擾�ł��܂���B�f�[�^�s�������������Ă��܂��B");
			}

			// ���O�C���L�����y�[��
			if (YesNo.YES.getValue().equals(dailyWork.getLoginCampaignFlg())) {
				// ���O�C���L�����y�[���̓�����ƌ��ʂ��擾����
				dailyWorkResult = DailyWorkResultProvider.getInstance().getDailyWorkResult(dailyWork.getUserId(),
						baseDate, ServiceType.LOGINCAMPAIGN.getValue());

				if (dailyWorkResult == null || Status.ERROR.getValue().equals(dailyWorkResult.getStatus())) {
					isExecuted = true;
				}
			}

			// �f�C���[�����[�h
			if (YesNo.YES.getValue().equals(dailyWork.getDailyRewardFlg())) {
				// �f�C���[�����[�h�̓�����ƌ��ʂ��擾����
				dailyWorkResult = DailyWorkResultProvider.getInstance().getDailyWorkResult(dailyWork.getUserId(),
						baseDate, ServiceType.DAILYREWARD.getValue());

				if (dailyWorkResult == null || Status.ERROR.getValue().equals(dailyWorkResult.getStatus())) {
					//					isExecuted = true;
				}
			}

			// ���[���b�g
			if (YesNo.YES.getValue().equals(dailyWork.getRouletteFlg())) {
				// ���[���b�g�̓�����ƌ��ʂ��擾����
				dailyWorkResult = DailyWorkResultProvider.getInstance().getDailyWorkResult(dailyWork.getUserId(),
						baseDate, ServiceType.ROULETTE.getValue());

				if (dailyWorkResult == null || Status.ERROR.getValue().equals(dailyWorkResult.getStatus())) {
					isExecuted = true;
				}
			}

			// �����Ώۃ��X�g�ɒǉ�����
			if (isExecuted) {
				this.targetDailyWorkList.add(dailyWork);
			}

			// ������
			dailyWorkResult = null;
			isExecuted = false;
		}

		LogUtil.info("[�����Ώی����F" + this.targetDailyWorkList.size() + "]");
	}

	@Override
	public void exec() throws ApplicationException, SystemException {

		for (DailyWork dailyWork : this.targetDailyWorkList) {

			// ���O�o�͗p�̃��[�UID��ݒ肷��
			LogUtil.setPropertyUserId(dailyWork.getUserId());

			//////////////////////////////////////////////////////
			// ���O�C������
			//////////////////////////////////////////////////////
			// ���[�U�����擾����
			User user = UserDao.getInstance().findByPKey(dailyWork.getUserId());

			LoginServiceBean loginServiceBean = new LoginServiceBean();
			loginServiceBean.setUserId(user.getUserId());
			loginServiceBean.setPassword(user.getPassword());
			loginServiceBean.setServiceType(ServiceType.NORMAL_LOGIN);

			try {
				// �r�W�l�X���W�b�N���Ăяo��
				RedstoneLoginBusinessLogic.getInstance().execute(loginServiceBean);
			} catch (Exception e) {
				LogUtil.error(e.getMessage(), e);

				try {
					// ���O�C���ς݂̏ꍇ�͂��邽�ߔO�̂��߃��O�A�E�g���Ă���
					// �r�W�l�X���W�b�N���Ăяo��
					RedstoneLogoutBusinessLogic.getInstance().execute(loginServiceBean);
				} catch (Exception ex) {
					LogUtil.error(ex.getMessage(), ex);
				}

				continue;
			}

			//////////////////////////////////////////////////////
			// ���O�C���L�����y�[��
			//////////////////////////////////////////////////////
			if (YesNo.YES.getValue().equals(dailyWork.getLoginCampaignFlg())) {
				DailyWorkServiceBean dailyWorkServiceBean = new DailyWorkServiceBean();
				dailyWorkServiceBean.setUserId(user.getUserId());
				dailyWorkServiceBean.setLogged(loginServiceBean.isLogged());

				try {
					// �r�W�l�X���W�b�N���Ăяo��
					RedStoneLoginCampaignBusinessLogic.getInstance().execute(dailyWorkServiceBean);
				} catch (Exception e) {
					LogUtil.error(e.getMessage(), e);
				}
			}

			//////////////////////////////////////////////////////
			// �f�C���[�����[�h
			//////////////////////////////////////////////////////
			// ������

			//////////////////////////////////////////////////////
			// ���[���b�g
			//////////////////////////////////////////////////////
			if (YesNo.YES.getValue().equals(dailyWork.getRouletteFlg())) {
				DailyWorkServiceBean dailyWorkServiceBean = new DailyWorkServiceBean();
				dailyWorkServiceBean.setUserId(user.getUserId());
				dailyWorkServiceBean.setLogged(loginServiceBean.isLogged());

				try {
					// �r�W�l�X���W�b�N���Ăяo��
					RedStoneRouletteBusinessLogic.getInstance().execute(dailyWorkServiceBean);
				} catch (Exception e) {
					LogUtil.error(e.getMessage(), e);
				}
			}

			//////////////////////////////////////////////////////
			// ���O�A�E�g����
			//////////////////////////////////////////////////////
			try {
				// �r�W�l�X���W�b�N���Ăяo��
				RedstoneLogoutBusinessLogic.getInstance().execute(loginServiceBean);
			} catch (Exception e) {
				LogUtil.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void post() throws ApplicationException, SystemException {
		// WEB�u���E�U�����
		WebDriverManager.getInstance().close();
	}

	/**
	 * ���C������
	 */
	public static void main(String[] args) {
		new DailyWorkBatch2().run(args);
	}
}
