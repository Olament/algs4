import edu.princeton.cs.algs4.Queue;
import java.util.HashSet;

public class BoggleSolver
{
    private Node root;
    private Trie trie;
    private int[] score = {0, 1, 2, 3, 5, 11};
    private int[] stringLength = {2, 4, 5, 6, 7, 8};

    public BoggleSolver(String[] aDictionary)
    {
        String[] dictionary = new String[aDictionary.length];
        trie = new Trie();
        for (int i = 0; i < aDictionary.length; i++)
        {
            dictionary[i] = aDictionary[i];
            trie.put(dictionary[i]);
        }
        root = trie.getRoot();
    }

    private static class Phase
    {
        private boolean[][] access;
        private char[] word;
        private Node node;
        private int col;
        private int row;
        private int length;
    }

    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        HashSet<String> validWords = new HashSet<>();
        int boardRow = board.rows();
        int boardCol = board.cols();

        for (int i = 0; i < boardRow; i++)
            for (int j = 0; j < boardCol; j++)
            {
                Queue<Phase> searchQueue = new Queue<>();
                Phase initialPhase = new Phase();

                //System.out.println("Row&Col: " + i + " " + j); //debug

                initialPhase.access = new boolean[boardRow][boardCol];
                initialPhase.access[i][j] = true;
                initialPhase.word = new char[boardRow * boardCol * 2];
                initialPhase.row = i;
                initialPhase.col = j;
                if (board.getLetter(i, j) == 'Q')
                {
                    initialPhase.word[0] = 'Q';
                    initialPhase.word[1] = 'U';
                    if (root.next[16] == null)
                        return new HashSet<String>();
                    initialPhase.node = root.next[16].next[20];
                    initialPhase.length = 2;
                }
                else
                {
                    initialPhase.word[0] = board.getLetter(i, j);
                    initialPhase.node = root.next[board.getLetter(i, j) - 65];
                    initialPhase.length = 1;
                }

                searchQueue.enqueue(initialPhase);

                while (!searchQueue.isEmpty())
                {
                    Phase currentPhase = searchQueue.dequeue();
                    int row = currentPhase.row;
                    int col = currentPhase.col;

                    //for (int p = 0; p < currentPhase.length; p++) //debug
                    //   System.out.print(currentPhase.word[p]);

                    if (currentPhase.node.isEndOfWord && currentPhase.length > 2)
                    {
                        StringBuilder string = new StringBuilder();

                        for (int k = 0; k < currentPhase.length; k++)
                            string.append(currentPhase.word[k]);

                        String wordString = string.toString();
                        if (!validWords.contains(wordString))
                            validWords.add(wordString);
                        
                        //System.out.print(" *"); //debug
                    }

                    for (int k = -1; k <= 1; k++)
                        for (int l = -1; l <= 1; l++)
                        {
                            int currentRow = row + k;
                            int currentCol = col + l;

                            if ((k == 0 && l == 0) ||
                                (currentRow < 0 || currentRow >= boardRow || currentCol < 0 || currentCol >= boardCol) ||
                                currentPhase.access[currentRow][currentCol])
                                continue;

                            char currentChar = board.getLetter(currentRow, currentCol);

                            if (currentChar == 'Q')
                            {
                                if (currentPhase.node.next[16] == null || 
                                    currentPhase.node.next[16].next[20] == null)
                                    continue;
                            }
                            else
                            {
                                if (currentPhase.node.next[currentChar - 65] == null)
                                    continue;
                            }

                            Phase nextPhase = new Phase();
                            nextPhase.access = copyOf(currentPhase.access);
                            nextPhase.access[currentRow][currentCol] = true;
                            nextPhase.word = currentPhase.word.clone();
                            nextPhase.row = currentRow;
                            nextPhase.col = currentCol;
                            if (currentChar == 'Q')
                            {
                                nextPhase.word[currentPhase.length] = 'Q';
                                nextPhase.word[currentPhase.length + 1] = 'U';
                                nextPhase.node = currentPhase.node.next[16].next[20];
                                nextPhase.length = currentPhase.length + 2;
                            }
                            else
                            {
                                nextPhase.word[currentPhase.length] = currentChar;
                                nextPhase.node = currentPhase.node.next[currentChar - 65];
                                nextPhase.length = currentPhase.length + 1;
                            }

                            searchQueue.enqueue(nextPhase);
                        }
                    
                    //System.out.println(); //debug
                    /*
                    for(int k = 0; k < boardRow; k++) //debug
                    {
                        for(int l = 0; l < boardCol; l++)
                            System.out.print(currentPhase.access[k][l] ? "1 " : "0 ");
                        System.out.println();
                    }
                    */
                }

                //System.out.println(); //debug
            }

        return validWords;
    }

    private boolean[][] copyOf(boolean[][] array)
    {
        boolean[][] newArray = new boolean[array.length][array[0].length];

        for (int i = 0; i < array.length; i++)
            for (int j = 0; j <array[0].length; j++)
                newArray[i][j] = array[i][j];

        return newArray;
    }

    public int scoreOf(String word)
    {
        if (!trie.contains(word))
            return 0;

        int pos = 0;

        for (int i = 0; i < score.length - 1; i++)
            if (word.length() > stringLength[pos])
                pos++;
            else
                break;

        return score[pos];
    }
}