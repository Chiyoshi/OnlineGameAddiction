package com.oga.app.service.businesslogic.redstone.login;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.service.beans.LoginServiceBean;
import com.oga.app.service.businesslogic.BusinessLogicBase;
import com.oga.app.service.provider.redstone.login.RedstoneLoginProvider;

/**
 * ログアウト処理
 */
public class RedstoneLogoutBusinessLogic extends BusinessLogicBase<LoginServiceBean> {

	/** ログアウト処理 */
	private static RedstoneLogoutBusinessLogic redstoneLogoutBusinessLogic;

	/** RedstoneLoginProvider */
	private RedstoneLoginProvider redstoneLoginProvider;

	/** ServiceBean */
	private LoginServiceBean loginServiceBean;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WebDriver
	 */
	public RedstoneLogoutBusinessLogic() {
		this.redstoneLoginProvider = RedstoneLoginProvider.getInstance();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedstoneLogoutBusinessLogic getInstance() {
		if (redstoneLogoutBusinessLogic == null) {
			redstoneLogoutBusinessLogic = new RedstoneLogoutBusinessLogic();
		}
		return redstoneLogoutBusinessLogic;
	}

	@Override
	protected void createServiceBean(LoginServiceBean serviceBean) {
		this.loginServiceBean = serviceBean;
	}

	/**
	 * データ取得およびチェック処理
	 * <pre>
	 * 何もしない
	 * </pre>
	 */
	@Override
	protected void checkSelectData() throws ApplicationException {
		// 何もしない
	}

	/**
	 * ログアウト処理
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {
		if (loginServiceBean.isLogged()) {
			LogUtil.info("[ログアウト処理] [START]");
			redstoneLoginProvider.logout();
			LogUtil.info("[ログアウト処理] [END]");
		}
	}
}
