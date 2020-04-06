package com.education.resources.datasource.mapper;

import org.apache.ibatis.jdbc.SQL;

public class BaseProvider {
	protected void filterFieldId(SQL sql, String fieldName, String[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		StringBuilder sbIds = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sbIds.append("'" + ids[i] + "'");
			if (i != ids.length - 1) {
				sbIds.append(",");
			}
		}
		sql.WHERE(fieldName + " IN (" + sbIds.toString() + ")");
	}


	protected void filterFieldId(StringBuilder sql, String fieldName, String[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		StringBuilder sbIds = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sbIds.append("'" + ids[i] + "'");
			if (i != ids.length - 1) {
				sbIds.append(",");
			}
		}
		sql.append(" "+fieldName + " IN (" + sbIds.toString() + ") ");
	}



	protected void filterFieldNotId(SQL sql, String fieldName, String[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		StringBuilder sbIds = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sbIds.append("'" + ids[i] + "'");
			if (i != ids.length - 1) {
				sbIds.append(",");
			}
		}
		sql.WHERE(fieldName + " NOT IN (" + sbIds.toString() + ")");
	}


	protected void filterFieldNotId(StringBuilder sql, String fieldName, String[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		StringBuilder sbIds = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sbIds.append("'" + ids[i] + "'");
			if (i != ids.length - 1) {
				sbIds.append(",");
			}
		}
		sql.append("and "+fieldName + " NOT IN (" + sbIds.toString() + ") ");
	}

}
