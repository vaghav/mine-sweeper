package com.company;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MineSweeperImpl implements MineSweeper {

    private static final String ASTERISK = "*";
    private static final String MINE_FIELD_REG_EXP = "^([\\.\\*\\n])+$";
    public static final String END_LINE_CHARACTER = "\n";
    private String[][] mineMatrix;
    private int rowsCount;

    @Override
    public void setMineField(String mineField) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(MINE_FIELD_REG_EXP);
        Matcher matcher = pattern.matcher(mineField);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        mineMatrix = initializeMatrix(mineField);
    }


    private String[][] initializeMatrix(String mineField) {
        String[] rows = mineField.split(END_LINE_CHARACTER);
        if (isJaggedArray(rows)) {
            throw new IllegalArgumentException();
        }
        rowsCount = rows.length;
        String[][] mineMatrix = new String[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++)
                mineMatrix[i][j] = String.valueOf(rows[i].charAt(j));
        }
        return mineMatrix;
    }

    private boolean isJaggedArray(String[] rows) {
        return !Arrays.stream(rows).map(r -> r.length()).allMatch(s -> s.equals(rows[0].length()));
    }

    @Override
    public String getHintField() throws IllegalStateException {
        for (int row = 0; row < mineMatrix.length; row++) {
            for (int column = 0; column < mineMatrix[row].length; column++)
                if (!isMine(row, column)) {
                    prepareHintMatrix(row, column);
                }
        }
        return getFormattedString();
    }

    private void prepareHintMatrix(int row, int column) {
        int sum = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i > mineMatrix.length - 1)
                continue;
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j > mineMatrix[i].length - 1)
                    continue;
                if (isMine(i, j)) {
                    sum = sum + 1;
                }
            }
        }
        mineMatrix[row][column] = String.valueOf(sum);
    }

    private String getFormattedString() {
        StringBuilder matrixStringBuilder = getConvertedMineMatrix();
        int everyForthIndex = matrixStringBuilder.toString().length();
        while (everyForthIndex > 0) {
            matrixStringBuilder.insert(everyForthIndex, END_LINE_CHARACTER);
            everyForthIndex = everyForthIndex - (rowsCount + 1);
        }
        return matrixStringBuilder.toString();
    }

    private StringBuilder getConvertedMineMatrix() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mineMatrix.length; i++) {
            for (int j = 0; j < mineMatrix[i].length; j++) {
                stringBuilder.append(mineMatrix[i][j]);
            }
        }
        return stringBuilder;
    }

    private boolean isMine(int i, int j) {
        return mineMatrix[i][j].equals(ASTERISK);
    }
}

