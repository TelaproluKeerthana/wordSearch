package com.wordsearch_api.wordSearch.services;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordgridService {

        public enum Direction {
            HORIZONTAL(0, 1),
            VERTICAL(1, 0),
            DIAGONAL(1, 1),
            REVERSE_HORIZONTAL(0, -1),
            REVERSE_VERTICAL(-1, 0),
            INVERSE_DIAGONAL(-1, -1);

            public final int dx;
            public final int dy;

            Direction(int dx, int dy) {
                this.dx = dx;
                this.dy = dy;
            }
        }

        public char[][] generateGrid(int x_axis, int y_axis, List<String> words) {
            char[][] matrix = new char[x_axis][y_axis];

            for (int i = 0; i < x_axis; i++) {
                for (int j = 0; j < y_axis; j++) {
                    matrix[i][j] = '-';
                }
            }

            Random random = new Random();

            for (String word : words) {
                boolean placed = false;
                List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
                Collections.shuffle(directions);  // Randomize direction selection

                for (Direction dir : directions) {
                    for (int x = 0; x < x_axis; x++) {
                        for (int y = 0; y < y_axis; y++) {
                            if (canPlaceWord(word, x, y, x_axis, y_axis, matrix, dir)) {
                                placeWord(word, x, y, matrix, dir);
                                placed = true;
                                break;
                            }
                        }
                        if (placed) break;
                    }
                    if (placed) break;
                }
            }
            fillEmptyCells(matrix, x_axis, y_axis);
            return matrix;
        }

        private boolean canPlaceWord(String word, int x, int y, int x_axis, int y_axis, char[][] matrix, Direction dir) {
            for (int i = 0; i < word.length(); i++) {
                int newX = x + i * dir.dx;
                int newY = y + i * dir.dy;
                if (newX < 0 || newY < 0 || newX >= x_axis || newY >= y_axis) return false;
                if (matrix[newX][newY] != '-' && matrix[newX][newY] != word.charAt(i)) return false;
            }
            return true;
        }

        private void placeWord(String word, int x, int y, char[][] matrix, Direction dir) {
            for (int i = 0; i < word.length(); i++) {
                matrix[x + i * dir.dx][y + i * dir.dy] = word.charAt(i);
            }
        }

        public void fillEmptyCells(char[][] matrix, int x_axis, int y_axis) {
            Random rand = new Random();
            for (int i = 0; i < x_axis; i++) {
                for (int j = 0; j < y_axis; j++) {
                    if (matrix[i][j] == '-') {
                        matrix[i][j] = (char) ('A' + rand.nextInt(26));
                    }
                }
            }
        }

        public void displayMatrix(char[][] matrix) {
            for (char[] row : matrix) {
                System.out.println(Arrays.toString(row).replace(",", " "));
            }
        }
    }
