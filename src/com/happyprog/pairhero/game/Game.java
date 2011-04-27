package com.happyprog.pairhero.game;

import com.happyprog.pairhero.subscribers.JUnitSubscriber;
import com.happyprog.pairhero.subscribers.RefactoringSubscriber;
import com.happyprog.pairhero.time.Timer;
import com.happyprog.pairhero.views.MainView;

public class Game {

	public static final int GREEN_TEST_POINTS = 10;
	public static final int REFACTORING_POINTS = 2;
	private int score;

	private final Timer timer;
	private final MainView view;
	private final Programmer leftProgrammer;
	private final Programmer rightProgrammer;
	private final JUnitSubscriber testSubscriber;
	private final RefactoringSubscriber refactoringSubscriber;

	public Game(MainView view, Timer timer, Programmer leftProgrammer, Programmer rightProgrammer,
			JUnitSubscriber testSubscriber, RefactoringSubscriber refactoringSubscriber) {
		this.view = view;
		this.timer = timer;
		this.leftProgrammer = leftProgrammer;
		this.rightProgrammer = rightProgrammer;
		this.testSubscriber = testSubscriber;
		this.refactoringSubscriber = refactoringSubscriber;
	}

	public void start() {
		testSubscriber.subscribe(this);
		refactoringSubscriber.subscribe(this);

		leftProgrammer.drive();
		rightProgrammer.observe();

		timer.start(this);

		view.updateScore(score);
	}

	public void stop() {
		timer.stop();
		testSubscriber.unregister();
		refactoringSubscriber.unregister();
	}

	public void onTimeChange(int seconds) {
		view.onTimeChange(seconds);
		leftProgrammer.onTimeChange();
		rightProgrammer.onTimeChange();

		if (seconds <= 0) {
			stop();
			view.onGameFinished("Awesome!");
			return;
		}
	}

	public void onSwitchRole() {
		leftProgrammer.switchRole();
		rightProgrammer.switchRole();
	}

	public void onGreenTest() {
		score += GREEN_TEST_POINTS;
		view.updateScore(score);
	}

	public void onRefactoring() {
		score += REFACTORING_POINTS;
		view.updateScore(score);
	}

}