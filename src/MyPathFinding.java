import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MyPathFinding {
    private JFrame frame;
    private int WIDTH = 850;
    private int HEIGHT = 660;

    private JPanel toolPanel = new JPanel();;

    private int tool = 0;
    private JButton searchButton = new JButton("Start search");
    private JButton resetButton = new JButton("Reset");
    private JButton genButton = new JButton("Generate map");
    private JButton clearButton = new JButton("Clear map");
    JLabel algoLabel = new JLabel("Algorithms");
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    private String[] algorithms = {"Dijkstra","A*"};
    JComboBox algosBox = new JComboBox<>(algorithms);
    private String[] tools = {"Start","Finish","Wall", "Eraser"};
    JComboBox toolBox = new JComboBox(tools);

    JLabel toolLabel = new JLabel("Toolbox");

    JLabel sizeLabel = new JLabel("Size:");
    JSlider size = new JSlider(1,5,2);
    private int cells = 20;
    private final int MSIZE = 600;
    private int CSIZE = MSIZE/cells;
    Map canvas;
    Node[][] map;
    private int startx = -1;
    private int starty = -1;
    private int finishx = -1;
    private int finishy = -1;
    private int delay = 30;
    private boolean solving = false;
    Algorithm Alg = new Algorithm();
    public static void main(String[] args) {
        new MyPathFinding();
    }


    public MyPathFinding() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Path Finding");
        frame.setLocationRelativeTo(null); // Set window position to center
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(true);
        toolPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Controls"));
        int space = 25;
        int buff = 45;

        toolPanel.setLayout(null);
        toolPanel.setBounds(10, 10, 220, 600);

        searchButton.setBounds(30, space, 150, 25);
        searchButton.addActionListener(e -> {
//            solving = true;
//            System.out.println("search button clicked");
            if((startx > -1 && starty > -1) && (finishx > -1 && finishy > -1)) {
                solving = true;
//                Alg.Dijkstra();

//                new Thread(() -> {
//                    solving = true;
//                    Alg.Dijkstra();
//                }).start();
            }
        });
        toolPanel.add(searchButton);
        space += buff;

        resetButton.setBounds(30, space, 150, 25);
        toolPanel.add(resetButton);
        space += buff;

        genButton.setBounds(30, space, 150, 25);
        toolPanel.add(genButton);
        space += buff;

        clearButton.setBounds(30,space, 150, 25);
        toolPanel.add(clearButton);
        space += buff;

        algoLabel.setBounds(30, space, 150, 25);
        toolPanel.add(algoLabel);
        space += 25;

        algosBox.setBounds(30, space, 150, 25);
        toolPanel.add(algosBox);
        space += buff;

        toolLabel.setBounds(30, space, 150, 25);
        toolPanel.add(toolLabel);
        space += 25;

        toolBox.setBounds(30, space, 150, 25);
        toolPanel.add(toolBox);
        space += buff;

        sizeLabel.setBounds(30, space, 150, 25);
        toolPanel.add(sizeLabel);
        space += 25;

        size.setMajorTickSpacing(10);
        size.setBounds(30, space, 150, 25);
        toolPanel.add(size);

        frame.getContentPane().add(toolPanel);

        canvas = new Map();
        canvas.setBounds(230, 10, MSIZE+1, MSIZE+1);
        frame.getContentPane().add(canvas);

        toolBox.addItemListener(e -> {
            tool = toolBox.getSelectedIndex();
        });
        clearMap();
        Alg.Dijkstra();
    }

    public void clearMap() {	//CLEAR MAP
        finishx = -1;	//RESET THE START AND FINISH
        finishy = -1;
        startx = -1;
        starty = -1;
        map = new Node[cells][cells];	//CREATE NEW MAP OF NODES
        for(int x = 0; x < cells; x++) {
            for(int y = 0; y < cells; y++) {
                map[x][y] = new Node(3,x,y);	//SET ALL NODES TO EMPTY
            }
        }
//        reset();	//RESET SOME VARIABLES
    }


    class Map extends JPanel implements MouseListener, MouseMotionListener {

        public Map() {
            System.out.println("in map constructor");
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void paintComponent(Graphics g) {
            System.out.println("paint component called");
            super.paintComponents(g);
            for(int x = 0; x < cells; x++) {	//PAINT EACH NODE IN THE GRID
                for(int y = 0; y < cells; y++) {
                    switch(map[x][y].getType()) {
                        case 0:
                            g.setColor(Color.GREEN); // start
                            break;
                        case 1:
                            g.setColor(Color.RED); // finish
                            break;
                        case 2:
                            g.setColor(Color.BLACK); // wall
                            break;
                        case 3:
                            g.setColor(Color.WHITE); // default
                            break;
                        case 4:
                            g.setColor(Color.CYAN); // searching
                            break;
                        case 5:
                            g.setColor(Color.YELLOW); // backtracking
                            break;
                    }

                    g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
                    g.setColor(Color.black);
                    g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);

                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            var x = e.getX()/CSIZE;
            var y = e.getY()/CSIZE;
            Node current = map[x][y];
//            current.setType(0);


            switch(tool ) {
                case 0: {	//START NODE
                    if(current.getType() != 2) {	//IF NOT WALL
                        if(startx > -1 && starty > -1) {	//IF START EXISTS SET IT TO EMPTY
                            map[startx][starty].setType(3);
                            map[startx][starty].setHops(-1);
                        }
                        current.setHops(0);
                        startx = x;	//SET THE START X AND Y
                        starty = y;
                        current.setType(0);	//SET THE NODE CLICKED TO BE START
                    }
                    break;
                }
                case 1: {//FINISH NODE
                    if(current.getType() != 2) {	//IF NOT WALL
                        if(finishx > -1 && finishy > -1)	//IF FINISH EXISTS SET IT TO EMPTY
                            map[finishx][finishy].setType(3);
                        finishx = x;	//SET THE FINISH X AND Y
                        finishy = y;
                        current.setType(1);	//SET THE NODE CLICKED TO BE FINISH
                    }
                    break;
                }
                default:
                    if(current.getType() != 0 && current.getType() != 1)
                            current.setType(tool);
                    break;
            }

            canvas.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX()/CSIZE;
            int y = e.getY()/CSIZE;
            Node current = map[x][y];
            System.out.println(tool);
            System.out.println(x);
            System.out.println(y);
            if((tool == 2 || tool == 3) && (current.getType() != 0 && current.getType() != 1))
                current.setType(tool);
            canvas.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        // Other MouseListener methods
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
    public void delay() {	//DELAY METHOD
        try {
            Thread.sleep(delay);
        } catch(Exception e) {}
    }
    class Algorithm {
        public void Dijkstra() {
            ArrayList<Node> priority = new ArrayList<Node>();	//CREATE A PRIORITY QUE
            priority.add(map[startx][starty]);	//ADD THE START TO THE QUE
            while(solving) {
                if(priority.size() <= 0) {	//IF THE QUE IS 0 THEN NO PATH CAN BE FOUND
                    solving = false;
                    break;
                }
                int hops = priority.get(0).getHops()+1;	//INCREMENT THE HOPS VARIABLE
                ArrayList<Node> explored = exploreNeighbors(priority.get(0), hops);	//CREATE AN ARRAYLIST OF NODES THAT WERE EXPLORED
                if(!explored.isEmpty()) {
                    priority.remove(0);	//REMOVE THE NODE FROM THE QUE
                    priority.addAll(explored);	//ADD ALL THE NEW NODES TO THE QUE
//                    Update();
                    System.out.println("repainting");
                    canvas.repaint();
                    delay();
                } else {	//IF NO NODES WERE EXPLORED THEN JUST REMOVE THE NODE FROM THE QUE
                    priority.remove(0);
                }
            }
        }

        //A STAR WORKS ESSENTIALLY THE SAME AS DIJKSTRA CREATING A PRIORITY QUE AND PROPAGATING OUTWARDS UNTIL IT FINDS THE END
        //HOWEVER ASTAR BUILDS IN A HEURISTIC OF DISTANCE FROM ANY NODE TO THE FINISH
        //THIS MEANS THAT NODES THAT ARE CLOSER TO THE FINISH WILL BE EXPLORED FIRST
        //THIS HEURISTIC IS BUILT IN BY SORTING THE QUE ACCORDING TO HOPS PLUS DISTANCE UNTIL THE FINISH
//        public void AStar() {
//            ArrayList<Node> priority = new ArrayList<Node>();
//            priority.add(map[startx][starty]);
//            while(solving) {
//                if(priority.size() <= 0) {
//                    solving = false;
//                    break;
//                }
//                int hops = priority.get(0).getHops()+1;
//                ArrayList<Node> explored = exploreNeighbors(priority.get(0),hops);
//                if(explored.size() > 0) {
//                    priority.remove(0);
//                    priority.addAll(explored);
//                    Update();
//                    delay();
//                } else {
//                    priority.remove(0);
//                }
//                sortQue(priority);	//SORT THE PRIORITY QUE
//            }
//        }
//
//        public ArrayList<Node> sortQue(ArrayList<Node> sort) {	//SORT PRIORITY QUE
//            int c = 0;
//            while(c < sort.size()) {
//                int sm = c;
//                for(int i = c+1; i < sort.size(); i++) {
//                    if(sort.get(i).getEuclidDist()+sort.get(i).getHops() < sort.get(sm).getEuclidDist()+sort.get(sm).getHops())
//                        sm = i;
//                }
//                if(c != sm) {
//                    Node temp = sort.get(c);
//                    sort.set(c, sort.get(sm));
//                    sort.set(sm, temp);
//                }
//                c++;
//            }
//            return sort;
//        }

        public ArrayList<Node> exploreNeighbors(Node current, int hops) {	//EXPLORE NEIGHBORS
            ArrayList<Node> explored = new ArrayList<Node>();	//LIST OF NODES THAT HAVE BEEN EXPLORED
            for(int a = -1; a <= 1; a++) {
                for(int b = -1; b <= 1; b++) {
                    int xbound = current.getX()+a;
                    int ybound = current.getY()+b;
                    if((xbound > -1 && xbound < cells) && (ybound > -1 && ybound < cells)) {	//MAKES SURE THE NODE IS NOT OUTSIDE THE GRID
                        Node neighbor = map[xbound][ybound];
                        if((neighbor.getHops()==-1 || neighbor.getHops() > hops) && neighbor.getType()!=2) {	//CHECKS IF THE NODE IS NOT A WALL AND THAT IT HAS NOT BEEN EXPLORED
                            explore(neighbor, current.getX(), current.getY(), hops);	//EXPLORE THE NODE
                            explored.add(neighbor);	//ADD THE NODE TO THE LIST
                        }
                    }
                }
            }
            return explored;
        }

        public void explore(Node current, int lastx, int lasty, int hops) {	//EXPLORE A NODE
            if(current.getType()!=0 && current.getType() != 1)	//CHECK THAT THE NODE IS NOT THE START OR FINISH
                current.setType(4);	//SET IT TO EXPLORED
            current.setLastNode(lastx, lasty);	//KEEP TRACK OF THE NODE THAT THIS NODE IS EXPLORED FROM
            current.setHops(hops);	//SET THE HOPS FROM THE START
//            checks++;
            if(current.getType() == 1) {	//IF THE NODE IS THE FINISH THEN BACKTRACK TO GET THE PATH
                backtrack(current.getLastX(), current.getLastY(),hops);
            }
        }

        public void backtrack(int lx, int ly, int hops) {	//BACKTRACK
//            length = hops;
            while(hops > 1) {	//BACKTRACK FROM THE END OF THE PATH TO THE START
                Node current = map[lx][ly];
                current.setType(5);
                lx = current.getLastX();
                ly = current.getLastY();
                hops--;
            }
            solving = false;
        }
    }
    class Node {

        // 0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = finalpath
        private int cellType = 0;
        private int hops;
        private int x;
        private int y;
        private int lastX;
        private int lastY;

        public Node(int type, int x, int y) {	//CONSTRUCTOR
            cellType = type;
            this.x = x;
            this.y = y;
            hops = -1;
        }

        public int getType() {return cellType;}

        public void setType(int type) {
            cellType = type;
        }

        public int getX() {return x;}		//GET METHODS
        public int getY() {return y;}
        public int getLastX() {return lastX;}
        public int getLastY() {return lastY;}
        public void setLastNode(int x, int y) {lastX = x; lastY = y;}
        public void setHops(int hops) {this.hops = hops;}
        public int getHops() {return hops;}

    }
}
