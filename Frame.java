//This is used as the main and and creates a the frame of the gui that opens first


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jfree.ui.RefineryUtilities;

public class Frame extends JFrame {

    private final JSplitPane splitPane;  
    private final JPanel leftPanel;      
    private final JPanel tab;   
    private final JScrollPane scrollPane; 
    private final JPanel inputPanel;      
    private final JButton addB;         
    private final JButton loadB;
    private final JButton saveB;
    private final JButton vizB;
    private final JButton about;
    private final JTable table;
    private final JPanel error;
    private final JPanel creators;
    private String regex = "[0-9]+";
    private Pattern p = Pattern.compile(regex);
    private Matcher m;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private boolean posDate = false;
    private boolean matching = false;
    private String[] header = {"ID","Last Name","First Name","Vaccine Type","Vaccination Date","Vaccine Location"};
    private Frame parent = this;
    private DefaultTableModel model;
    private Dictionary filepath = new Hashtable<Integer, String[]>();
    private int filenum = 0;
    private String saveFile;
    private Dictionary base = new Hashtable<Integer, String[]>();
    private FileTools filer = new FileTools();
    private DatabaseTool tool = new DatabaseTool();
    
    public Frame()
    {
        dateFormat.setLenient(false);
        error = new JPanel();
        creators = new JPanel();
        splitPane = new JSplitPane();
        leftPanel = new JPanel();
              tab = new JPanel();

        
        model = new DefaultTableModel();
        table = new JTable();
        table.setModel(model);
        table.setRowHeight(30);
        scrollPane = new JScrollPane(table);

        inputPanel = new JPanel();
        about = new JButton("About");
        about.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               parent.about();   
            }
        });
        loadB = new JButton("Load");
        loadB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Menus loading = new Menus(parent, 0);
                RefineryUtilities.centerFrameOnScreen( loading ); 
                loading.setVisible(true);
                
            }
        });
        saveB = new JButton("Save");
        saveB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                Menus saving = new Menus(parent, 2);
                RefineryUtilities.centerFrameOnScreen( saving ); 
                saving.setVisible(true);

            }
        });
        vizB = new JButton("Visualize");
        vizB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                tool.sumVaccType(base);     
                tool.sumVaccLocation(base);      

            }
        });

        addB = new JButton("Add");
        addB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Menus adding = new Menus(parent, 1);
                RefineryUtilities.centerFrameOnScreen( adding ); 
                adding.setVisible(true);

            }
        });

        setPreferredSize(new Dimension(800, 500));

        getContentPane().setLayout(new GridLayout());

        getContentPane().add(splitPane);

        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(200);
        splitPane.setRightComponent(tab);
        splitPane.setLeftComponent(leftPanel);

        tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));

        tab.add(scrollPane);

        leftPanel.add(inputPanel);

        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        inputPanel.setLayout(new GridLayout(6, 1));

        inputPanel.add(saveB);
        inputPanel.add(vizB);
        inputPanel.add(addB);
        inputPanel.add(loadB);
        inputPanel.add(about);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
    }

    public void updateTab(String[] list)
    {
        matching = false;
        for(int x = 0; x < base.size(); x++)
        {
            if(((String []) base.get(x))[0].equals(list[0]))
            {
                JOptionPane.showMessageDialog(new JOptionPane(), "Replaced Data for the following ID: " + list[0], "Notification", JOptionPane.NO_OPTION);
                base.put(x, list);
                model.removeRow(x-1);
                model.insertRow(x-1,list);
                matching = true;
                x = base.size();
            }
 
        }
        if(!matching)
        {
            parent.headerStart(header);
            model.addRow(list);
            base.put(base.size(), list);
        }

    }

    public void loader(String file) {
        matching = false;
        for(int i=0; i < filepath.size(); i++)
        {
            if(((String) filepath.get(i)).equals(file))
            {
                matching = true;
                i = filepath.size();
            }
        }
        if(!matching)
        {
            filepath.put(filenum, file); //scan.nextLine(); //Read in file path
            base = filer.ReadFile((String)filepath.get(filenum), parent, base);
            filenum++;
        }
        else
        {
            JOptionPane.showMessageDialog(new JOptionPane(), "Duplicate file uploaded", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public void Saver(String fileName)
    {
        saveFile = fileName;
        if (saveFile.endsWith(".csv") != true) {
            saveFile = saveFile + ".csv";
        }
        filer.SaveFile(saveFile, base);
        
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Frame gui = new Frame();
                gui.setTitle("Vaccinations");
                RefineryUtilities.centerFrameOnScreen( gui );
                gui.setVisible(true);
            }
        });
    }

    public void about()
    {
        JOptionPane.showMessageDialog(creators, "Made By: Jonathan Franco, Shuiqiang Lin, and Kristian Meling", "About", JOptionPane.NO_OPTION);
    }

    public void emptyFile()
    {
        JOptionPane.showMessageDialog(error, "File is empty", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void headerStart(String[] row) 
    {
        model.setColumnIdentifiers(row);
    }

    public boolean validinput(String[] input)
    {
        posDate = false;
        m = p.matcher(input[0]);
        try
        {
           dateFormat.parse(input[4]);
        } 
        catch(ParseException e)
        {
           posDate = true;
        }

        if(!m.matches() || input[0].length() > 5 || p.matcher(input[1]).matches() || p.matcher(input[2]).matches() || 
            p.matcher(input[3]).matches() || p.matcher(input[5]).matches() || posDate)
        {

           JOptionPane.showMessageDialog(new JOptionPane(), "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
           return false;
        }
        return true;        
    }
}
