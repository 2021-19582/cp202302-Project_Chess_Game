import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
//======================================================Don't modify below===============================================================//
enum PieceType {king, queen, bishop, knight, rook, pawn, none} // PieceType is the state of each square
enum PlayerColor {black, white, none}

public class ChessBoard {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel chessBoard;
    private JButton[][] chessBoardSquares = new JButton[8][8]; // each chessBoardSquare is a button
    private Piece[][] chessBoardStatus = new Piece[8][8];
    private ImageIcon[] pieceImage_b = new ImageIcon[7];
    private ImageIcon[] pieceImage_w = new ImageIcon[7];
    private JLabel message = new JLabel("Enter Reset to Start");

    ChessBoard(){ // constructor
        initPieceImages();
        initBoardStatus();
        initializeGui();
    }

    public final void initBoardStatus(){ // create chessBoard w/o b/w pieces
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) chessBoardStatus[j][i] = new Piece();
        }
    }

    public final void initPieceImages(){ // icons
        pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));

        pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
    }

    public ImageIcon getImageIcon(Piece piece){ // assign Piece instances to icons
        if(piece.color.equals(PlayerColor.black)){
            if(piece.type.equals(PieceType.king)) return pieceImage_b[0];
            else if(piece.type.equals(PieceType.queen)) return pieceImage_b[1];
            else if(piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
            else if(piece.type.equals(PieceType.knight)) return pieceImage_b[3];
            else if(piece.type.equals(PieceType.rook)) return pieceImage_b[4];
            else if(piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
            else return pieceImage_b[6];
        }
        else if(piece.color.equals(PlayerColor.white)){
            if(piece.type.equals(PieceType.king)) return pieceImage_w[0];
            else if(piece.type.equals(PieceType.queen)) return pieceImage_w[1];
            else if(piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
            else if(piece.type.equals(PieceType.knight)) return pieceImage_w[3];
            else if(piece.type.equals(PieceType.rook)) return pieceImage_w[4];
            else if(piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
            else return pieceImage_w[6];
        }
        else return pieceImage_w[6];
    }

    public final void initializeGui(){
        gui.setBorder(new EmptyBorder(5, 5, 5, 5)); // gui is a JPanel
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton startButton = new JButton("Reset");
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                initiateBoard();
            }
        });

        tools.add(startButton);
        tools.addSeparator();
        tools.add(message);

        chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        Insets buttonMargin = new Insets(0,0,0,0);
        for(int i=0; i<chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton b = new JButton();
                b.addActionListener(new ButtonListener(i, j));
                b.setMargin(buttonMargin);
                b.setIcon(defaultIcon);
                if((j % 2 == 1 && i % 2 == 1)|| (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
                else b.setBackground(Color.gray);
                b.setOpaque(true);
                b.setBorderPainted(false);
                chessBoardSquares[j][i] = b;
            }
        }

        for (int i=0; i < 8; i++) {
            for (int j=0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);

        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ChessBoard cb = new ChessBoard();
                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.setResizable(false);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }

    //================================Utilize these functions========================================//

    class Piece{
        PlayerColor color;
        PieceType type;

        Piece(){
            color = PlayerColor.none;
            type = PieceType.none;
        }
        Piece(PlayerColor color, PieceType type){
            this.color = color;
            this.type = type;
        }
    }

    public void setIcon(int x, int y, Piece piece){ // move Piece to
        chessBoardSquares[y][x].setIcon(getImageIcon(piece));
        chessBoardStatus[y][x] = piece;
    }

    public Piece getIcon(int x, int y){
        return chessBoardStatus[y][x];
    }

    public void markPosition(int x, int y){
        chessBoardSquares[y][x].setBackground(Color.pink);
    }

    public void unmarkPosition(int x, int y){
        if((y % 2 == 1 && x % 2 == 1)|| (y % 2 == 0 && x % 2 == 0)) chessBoardSquares[y][x].setBackground(Color.WHITE);
        else chessBoardSquares[y][x].setBackground(Color.gray);
    }

    public void setStatus(String inpt){
        message.setText(inpt);
    } // message is JLabel

    public void initiateBoard(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) setIcon(i, j, new Piece());
        }
        setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
        setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
        setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
        setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
        for(int i=0;i<8;i++){
            setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
            setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
        }
        setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
        setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
        setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
        setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) unmarkPosition(i, j);
        }
        onInitiateBoard();
    }
//======================================================Don't modify above==============================================================//	




    //======================================================Implement below=================================================================//
    enum MagicType {MARK, CHECK, CHECKMATE};
    private int selX, selY; // selectX, selectY
    private boolean check, checkmate, end;

    private boolean turn; // false: Black, true: White
    private boolean amIPink; // false: select Piece, true: select where to move




    class ButtonListener implements ActionListener{
        int x;
        int y;
        ButtonListener(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void actionPerformed(ActionEvent e) {	// Only modify here
            // (x, y) is where the click event occured
//            System.out.println("clicked: "+x+","+y);
            if (end) return;
            if (!amIPink || (amIPink && (getIcon(x, y).color==PlayerColor.black && !turn) || (getIcon(x, y).color==PlayerColor.white && turn))){

                if (getIcon(x, y).color == PlayerColor.none) return;
                if (turn != (getIcon(x, y).color == PlayerColor.white)) return;// choose diff piece!
                ArrayList<int[]> move, eat;
                ArrayList<int[]>[] temp = moveOrEatSquare(getIcon(x, y), x, y);
                move = temp[0];

                if (amIPink){
                    ArrayList<int[]> moveSel = moveOrEatSquare(getIcon(selX, selY), selX, selY)[0];
                    for (int[] s: moveSel){
                        unmarkPosition(s[0], s[1]);
                    }
                }
                for (int[] s: move){
                    markPosition(s[0], s[1]);
                }
                selX = x;
                selY = y;
                amIPink = true;
            }
            else{
                ArrayList<int[]> move, eat;
                ArrayList<int[]>[] temp = moveOrEatSquare(getIcon(selX, selY), selX, selY);
                move = temp[0]; // places where clicked icon can move
                eat = temp[1];
                for(int[] s: move){
//                    System.out.println(s[0]+","+s[1]);
                    if (s[0] == x && s[1] == y){
//                        System.out.println("contains!");
                        if (getIcon(x,y).type==PieceType.king)end = true;
                        setIcon(x, y, getIcon(selX, selY));
                        setIcon(selX, selY, new Piece(PlayerColor.none, PieceType.none));
                        turn = !turn;
                        endOfTurn();
                    }
                }

                for (int[] s: move){
                    unmarkPosition(s[0], s[1]);
                }
                amIPink = false;



            }


        }
    }

    void endOfTurn(){ // TODO: https://stackoverflow.com/questions/30401046/check-of-checkmate-in-chess
        
        if (end) {
            setStatus("END");
            return;
        }
        check = false;
//        checkmate = false;
        ArrayList<int[]> totalEat = new ArrayList<>(), kingMove = new ArrayList<>();
        // turn == false: black
        // turn == true: white
        int kingX = 0;
        int kingY = 0;
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j ++){
                if((!turn && (getIcon(i , j).color==PlayerColor.white))||(turn && (getIcon(i , j).color==PlayerColor.black))) totalEat.addAll(moveOrEatSquare(getIcon(i, j), i, j)[1]);
                else if ((!turn && (getIcon(i, j).color==PlayerColor.black && (getIcon(i, j).type == PieceType.king))) ||
                        (turn && (getIcon(i, j).color==PlayerColor.white && (getIcon(i, j).type == PieceType.king)))) {
                    kingX = i;
                    kingY = j;
                    kingMove = moveOrEatSquare(getIcon(i, j), i, j)[0];
                }
            }
        }
        for (int[] s: totalEat){
            if (s[0]==kingX && s[1]==kingY){
                check = true;
//                checkmate = true;
            }
        }
        for (int[] s : kingMove) if(!totalEat.contains(s)) checkmate=false;

        for(int[] s2: kingMove){
            boolean temp = false;
            for (int[] s: totalEat){
                if(s[0]==s2[0] && s[1]==s2[1]) temp = true;
            }
            if(!temp) {
                checkmate = false;
                break;
            }
        }

        if(turn) // white's turn
        {
//            if (checkmate) {
//                end = true;
//                setStatus("WHITE's TURN / CHECKMATE");
//            }
            if (check)setStatus("WHITE's TURN / CHECK");
            else setStatus("WHITE's TURN");
        }
        else {
//            if (checkmate) {
//                end = true;
//                setStatus("BLACK's TURN / CHECKMATE");
//            }
            if (check)setStatus("BLACK's TURN / CHECK");
            else setStatus("BLACK's TURN");
        }


    }

    ArrayList<int []>[] moveOrEatSquare(Piece piece, int x, int y){
        ArrayList<int []> move = new ArrayList<>();
        ArrayList<int []> eat = new ArrayList<>();
        switch(piece.type){
            case king: {
                int[][] temp = {{x, y+1}, {x, y-1}, {x-1, y}, {x+1, y}, {x+1, y+1}, {x+1, y-1}, {x-1, y+1}, {x-1, y-1}};
                for (int[] s: temp) {
                    if (s[0] < 0 || s[0] > 7 || s[1] < 0 || s[1] > 7 || getIcon(s[0],s[1]).color==piece.color) continue;
                    else move.add(s);
                    if (getIcon(s[0], s[1]).type!=PieceType.none) eat.add(s);
                } break;
            }
            case queen:
            case rook: {
                for (int[] op : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                    int i = 1;
                    for (i = 1; i < 8; i++) {
                        if (x + op[0] * i > 7 || x + op[0] * i < 0 || y + op[1] * i > 7 || y + op[1] * i < 0) break;
                        else if (getIcon(x + op[0] * i, y + op[1] * i).type != PieceType.none) break;
                        else move.add(new int[]{x + op[0] * i, y + op[1] * i});
                    }
                    if (!(x + op[0] * i > 7 || x + op[0] * i < 0 || y + op[1] * i > 7 || y + op[1] * i < 0)&&getIcon(x + op[0] * i, y + op[1] * i).type != PieceType.none && getIcon(x + op[0] * i, y + op[1] * i).color!=piece.color) {
                        move.add(new int[]{x + op[0] * i, y + op[1] * i});
                        eat.add(new int[]{x + op[0] * i, y + op[1] * i});
                    }
                }
                if (piece.type == PieceType.rook) break;
            }

            case bishop: {
                for (int[] op: new int[][]{{1, 1},{1, -1}, {-1, 1}, {-1, -1}}){
                    int i = 1;
                    for (i = 1; i < 8; i++) {
                        if (x + op[0] * i > 7 ||x + op[0] * i <0|| y + op[1] * i > 7 || y + op[1] * i<0) break;
                        else if (getIcon(x + op[0] * i, y + op[1] * i).type != PieceType.none) break;
                        else move.add(new int[]{x + op[0] * i, y + op[1] * i});
                    }
                    if (!(x + op[0] * i > 7 ||x + op[0] * i <0|| y + op[1] * i > 7 || y + op[1] * i<0)&&getIcon(x + op[0] * i, y + op[1] * i).type != PieceType.none && getIcon(x + op[0] * i, y + op[1] * i).color!=piece.color) {
                        move.add(new int[]{x + op[0] * i, y + op[1] * i});
                        eat.add(new int[]{x + op[0] * i, y + op[1] * i});
                    }
                }
                break;
            }

            case knight:{
                for (int[] s: new int[][]{{2, 1},{2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}}){
                    if(s[0]+x<0 || s[0]+x>7 || s[1]+y<0 || s[1]+y>7 || getIcon(x+s[0], y+s[1]).color==piece.color) continue;
                    else if (getIcon(x+s[0], y+s[1]).type!=PieceType.none) eat.add(new int[]{x+s[0], y+s[1]});
                    move.add(new int[]{x+s[0], y+s[1]});
                }
                break;
            }

            case pawn:{
                if (piece.color==PlayerColor.black){
                    if(x+1<8 && getIcon(x+1, y).type==PieceType.none) {
                        move.add(new int[]{x + 1, y});
                        if(x==1&&getIcon(x+2, y).type==PieceType.none){
                                move.add(new int[]{x+2, y});
                        }
                    }
                    if(y-1>=0 && x+1<=7 && getIcon(x+1, y-1).color==PlayerColor.white){
                        move.add(new int[]{x+1, y-1});
                        eat.add(new int[]{x+1, y-1});
                    }
                    if(y+1<=7 && x+1<=7 && getIcon(x+1, y+1).color==PlayerColor.white){
                        move.add(new int[]{x+1, y+1});
                        eat.add(new int[]{x+1, y+1});
                    }

                }
                else{
                    if(x-1>=0 && getIcon(x-1, y).color==PlayerColor.none) {
                        move.add(new int[]{x-1, y});
                        if(x==6&&getIcon(x-2, y).color==PlayerColor.none){
                            move.add(new int[]{x-2, y});
                        }
                    }
                    if(y-1>=0 && x-1>=0 && getIcon(x-1, y-1).color==PlayerColor.black){
                        move.add(new int[]{x-1, y-1});
                        eat.add(new int[]{x-1, y-1});
                    }
                    if(y+1<=7 && x-1>=0 && getIcon(x-1, y+1).color==PlayerColor.black){
                        move.add(new int[]{x-1, y+1});
                        eat.add(new int[]{x-1, y+1});
                    }
                }
                break;
            }

            default: break;
        }
        return new ArrayList[]{move, eat};
    }

    void onInitiateBoard(){
        setStatus("BLACK's TURN");
        turn = false;
        end = false;
        check = false;
//        checkmate = false;
    }
}