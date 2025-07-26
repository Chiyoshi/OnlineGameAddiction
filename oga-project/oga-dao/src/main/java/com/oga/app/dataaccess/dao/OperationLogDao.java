package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.OperationLogMapper;
import com.oga.app.dataaccess.entity.OperationLog;

/**
 * ���샍�O�e�[�u���𑀍삷��N���X
 */
public class OperationLogDao extends BaseDao {

	/** ���샍�O�e�[�u��DAO */
	private static OperationLogDao operationLogDao;

	/**
	 * �R���X�g���N�^
	 */
	private OperationLogDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized OperationLogDao getInstance() {
		if (operationLogDao == null) {
			operationLogDao = new OperationLogDao();
		}
		return operationLogDao;
	}

	/**
	 * ���샍�O���擾����
	 * 
	 * @return ���샍�O���X�g
	 */
	public List<OperationLog> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			OperationLogMapper mapper = session.getMapper(OperationLogMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * �����������w�肵�đ��샍�O���擾����
	 * 
	 * @param fromOperationDate ������tFrom
	 * @param toOperationDate ������tTo
	 * @param userId ���[�UID
	 * @return
	 */
	public List<OperationLog> findByOperationDate(String fromOperationDate, String toOperationDate, String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			OperationLogMapper mapper = session.getMapper(OperationLogMapper.class);
			return mapper.findByOperationDate(fromOperationDate, toOperationDate, userId);
		}
	}

	/**
	 * ���샍�O���擾����
	 * 
	 * @return ���샍�O���X�g
	 */
	public void insertOperationLog(OperationLog operationLog) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			OperationLogMapper mapper = session.getMapper(OperationLogMapper.class);
			mapper.insertOperationLog(operationLog);
			session.commit();
		}
	}
}
