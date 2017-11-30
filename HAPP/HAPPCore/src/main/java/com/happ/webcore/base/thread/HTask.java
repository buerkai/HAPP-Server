package com.happ.webcore.base.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by hyc on 17/2/15.
 */

public abstract class HTask implements Runnable {

	// 是否在执行当前任务
	private int runStatus = 0;

	private ThreadPoolExecutor executor;

	private Thread currentThread;

	private long statTime;
	private int timeOut;

	private Object param;

	private List<HTask> list = new ArrayList<HTask>();

	private HTask parentTask;

	public HTask() {
		statTime = System.currentTimeMillis();
		executor = HExecutor.getInstance().getExecutor();
	}

	public HTask(Object param) {
		statTime = System.currentTimeMillis();
		this.param = param;
		executor = HExecutor.getInstance().getExecutor();
	}

	public HTask setTimeout(int timeOut) {
		this.timeOut = timeOut;
		return this;
	}

	public HTask setParam(Object param) {
		this.param = param;
		return this;
	}

	public void run() {
		try {
			this.currentThread = Thread.currentThread();
			if (timeOut > 0 && System.currentTimeMillis() - statTime > timeOut * 1000 - 10) {
				return;
			}
			this.runStatus = 1;
			Object o = this.doTask(this.param);
			for (HTask task : list) {
				task.setParam(o);
				this.executor.execute(task);
			}
		}  finally {
			this.runStatus = 2;
			list.clear();
			list = null;
		}
	}

	public boolean isRunning() {
		return this.runStatus == 1;
	}

	public boolean isComplete() {
		return this.runStatus == 2;
	}

	public void cancle() {
		if (this.runStatus == 0) {
			if (parentTask != null) {
				parentTask.list.remove(this);
			}
			this.executor.remove(this);
		} else if (this.runStatus == 1 && currentThread != null) {
			currentThread.interrupt();
			this.executor.remove(this);
		}
	}

	public HTask exe() {
		this.executor.execute(this);
		return this;
	}

	public HTask addTask(HTask task) {
		this.executor.execute(task);
		return this;
	}

	public HTask addTaskAfter(HTask task) {
		if (this.hashCode() == task.hashCode()) {
			throw new RuntimeException("添加任务错误，不能添加任务本身");
		}
		task.parentTask = this;
		synchronized (list) {
			if (!list.contains(task)) {
				list.add(task);
			}
		}
		return this;
	}

	public abstract Object doTask(Object param);

	@Override
	public int hashCode() {
		return (int) (statTime + super.hashCode());
	}

}