package com.oga.app.service.businesslogic;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;

public abstract class BusinessLogicBase<T> {

	// トランザクション管理用のメソッド
	public void execute(T param) throws Exception {

		try {
			// トランザクションの開始
			beginTransaction();

			// ServiceBeanを設定する
			createServiceBean(param);

			// データ取得およびチェック処理
			checkSelectData();

			// ビジネスロジックの処理
			doBusinessLogic();

			// トランザクションのコミット
			commitTransaction();

		} catch (ApplicationException e) {
			// エラーハンドリング
			handleError(e);

			// トランザクションのロールバック
			rollbackTransaction();

			throw e;
		} catch (SystemException e) {
			throw e;
		}
	}

	/**
	 * ServiceBeanを設定する
	 * 各ビジネスロジッククラスが実装すべきメソッド
	 * 
	 * @param param serviceBean
	 * @throws ApplicationException
	 */
	protected abstract void createServiceBean(T param) throws ApplicationException;

	/**
	 * データ取得およびチェック処理
	 * 各ビジネスロジッククラスが実装すべきメソッド
	 */
	protected abstract void checkSelectData() throws ApplicationException, SystemException;

	/**
	 * ビジネスロジック
	 * 各ビジネスロジッククラスが実装すべきメソッド
	 */
	protected abstract void doBusinessLogic() throws ApplicationException, SystemException;

	/**
	 * トランザクション開始
	 */
	protected void beginTransaction() {
		// トランザクション開始処理
		// System.out.println("Transaction started.");
	}

	/**
	 * トランザクションコミット
	 */
	protected void commitTransaction() {
		// コミット処理
		//System.out.println("Transaction committed.");
	}

	/**
	 * トランザクションロールバック
	 */
	protected void rollbackTransaction() {
		// ロールバック処理
		// System.out.println("Transaction rolled back.");
	}

	/**
	 * エラーハンドリング処理 
	 * @param e
	 */
	protected void handleError(Exception e) {
		// ログ出力やエラーハンドリング
		// System.err.println("Error occurred: " + e.getMessage());
		// e.printStackTrace();
	}
}
