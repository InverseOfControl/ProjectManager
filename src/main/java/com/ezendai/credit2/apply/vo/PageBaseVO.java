package com.ezendai.credit2.apply.vo;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.vo.BaseVO;

public class PageBaseVO extends BaseVO {
	private static final long serialVersionUID = 8573297497874330936L;

	private int rows;
	private int page;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
		if (super.getPager() == null)
			super.setPager(new Pager());
		super.getPager().setRows(rows);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		super.getPager().setPgNumber(page);
	}

}
