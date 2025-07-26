package com.oga.app.batch;

import java.util.ArrayList;
import java.util.List;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.prop.OGAProperty;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.dao.UserDao;
import com.oga.app.dataaccess.entity.DailyWork;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.businesslogic.redstone.RedStoneLoginCampaignBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedStoneRouletteBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedstoneLoginBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedstoneLogoutBusinessLogic;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.provider.DailyWorkProvider;
import com.oga.app.service.servicebeans.DailyWorkServiceBean;
import com.oga.app.service.servicebeans.LoginServiceBean;

public class DailyWorkBatch extends BaseBatch {

	/** �����Ώ� */
	private List<DailyWork> targetDailyWorkList = new ArrayList<DailyWork>();

	/** ���O�C���G���[�����e���錏��(�����l�F2) */
	private int LOGIN_ERROR_ALLOW_COUNT = 2;

	/**
	 * �R���X�g���N�^
	 */
	public DailyWorkBatch() {
	}

	@Override
	public void pre(String[] args) throws ApplicationException, SystemException {

		// ���O�C���G���[�����e���錏����ݒ肷��
		this.LOGIN_ERROR_ALLOW_COUNT = Integer.parseInt(OGAProperty.getProperty("login.error.allow.count"));
		
		// ������擾����
		String baseDate = DateUtil.getBaseDate();

		// ������Ƃ̑Ώێ҂��擾����
		this.targetDailyWorkList = DailyWorkProvider.getInstance().getTargetDailyWorklList(baseDate);

		LogUtil.info("[�����Ώی����F" + this.targetDailyWorkList.size() + "]");
	}

	@Override
	public void exec() throws ApplicationException, SystemException {

		// ���O�C���G���[����
		int loginErrorCount = 0;
		
		for (DailyWork dailyWork : this.targetDailyWorkList) {

			// ���O�C���G���[���A�������ꍇ�͈�U�u���E�U����ăL���b�V�����N���A����
			if (this.LOGIN_ERROR_ALLOW_COUNT <= loginErrorCount) {
				WebDriverManager.getInstance().close();

				// ���O�C���G���[����������������
				loginErrorCount = 0;
			}

			// WebDriverManager�𐶐����ău���E�U���J��
			WebDriverManager.getInstance();

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

				//try {
				//	// ���O�C���ς݂̏ꍇ�͔O�̂��߃��O�A�E�g���Ă���
				//	// �r�W�l�X���W�b�N���Ăяo��
				//	RedstoneLogoutBusinessLogic.getInstance().execute(loginServiceBean);
				//} catch (Exception ex) {
				//	LogUtil.error(ex.getMessage(), ex);
				//}

				loginErrorCount++;

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

			// ���O�C���G���[����������������
			loginErrorCount = 0;
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
		new DailyWorkBatch().run(args);
	}
}
