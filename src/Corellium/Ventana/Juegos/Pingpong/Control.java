package Corellium.Ventana.Juegos.Pingpong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Random;

class Control {

    private double width = 1000;
    private double height = 700;
    private static final double JUGADOR_HEIGHT = 100;
    private static final double JUGADOR_WIDTH = 15;
    private static final int BALL_RADIO = 15;
    private double ballYSpeed = 1;
    private double ballXSpeed = 1;
    private double playerOneYPos = height / 2;
    private double playerTwoYPos = height / 2;
    private double ballXPos = width / 2;
    private double ballYPos = height / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted;
    private int playerOneXPos = 0;
    private double playerTwoXPos = width - JUGADOR_WIDTH;

    Control() { }

    Control(double width, double height) {
        this.width = width;
        this.height = height;
    }

    void start(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        t1.setCycleCount(Timeline.INDEFINITE);
        canvas.setHeight(height);
        canvas.setWidth(width);
        canvas.setOnMouseMoved(event -> {
            playerOneYPos = event.getY();
        });
        canvas.setOnMouseClicked(mouseEvent -> {
            gameStarted = true;
        });
        t1.play();
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,width,height);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        if(gameStarted) {

            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;

            if(ballXPos < width - (width / 4)) {
                playerTwoYPos = ballYPos - JUGADOR_HEIGHT / 2;
            }  else {
                playerTwoYPos =  ballYPos > playerTwoYPos + JUGADOR_HEIGHT / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
            }
            gc.fillOval(ballXPos, ballYPos, BALL_RADIO, BALL_RADIO);
        } else {
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Clickeame", width / 2, height / 2);
            ballXPos = width / 2;
            ballYPos = height / 2;
            ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
        }

        if(ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
        if(ballXPos < playerOneXPos - JUGADOR_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }
        if(ballXPos > playerTwoXPos + JUGADOR_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }

        if( ((ballXPos + BALL_RADIO > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + JUGADOR_HEIGHT) ||
                ((ballXPos < playerOneXPos + JUGADOR_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + JUGADOR_HEIGHT)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }
        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
        gc.fillRect(playerTwoXPos, playerTwoYPos, JUGADOR_WIDTH, JUGADOR_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, JUGADOR_WIDTH, JUGADOR_HEIGHT);
    }
}
