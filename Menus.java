//These control the menus that appear when using the add, load and save functionality

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class Menus extends JFrame{

   private JPanel menu, bPane;
   private static JButton submit;
   private static JLabel id, lastName, firstName, type, date, location, dataPath, fileName;
   private static JTextField idIn, lastIn, firstIn, typeIn, dateIn, locationIn, pathIn, fileIn;
   private final Menus container;

   public Menus(Frame parent, int choice)
   {
      container = this;
      if (choice == 0)
         loadMenu(parent, container);
      if (choice == 1)
         addMenu(parent, container);
      if (choice == 2)
         saveMenu(parent, container);
      
   }

   public void saveMenu(Frame parent, Menus container)
   {
      menu = new JPanel();
      bPane = new JPanel();
      fileName = new JLabel("Please input a file name");
      fileIn = new JTextField();

      submit = new JButton("submit");
      submit.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            parent.Saver(fileIn.getText());
            container.dispose();

         }
      });



      setPreferredSize(new Dimension(400, 150));

      getContentPane().setLayout(new BorderLayout());

      getContentPane().add(menu, BorderLayout.CENTER);

      getContentPane().add(bPane, BorderLayout.PAGE_END);
      menu.setBorder(new EmptyBorder(10, 10, 10, 10));
      menu.setLayout(new GridLayout(2, 1, 1, 1));
      menu.add(fileName);
      menu.add(fileIn);
      bPane.add(submit);

      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      pack();

   }


   public void loadMenu(Frame parent, Menus container)
   {
      menu = new JPanel();
      bPane = new JPanel();
      dataPath = new JLabel("Please input a file path");
      pathIn = new JTextField();

      submit = new JButton("submit");
      submit.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            parent.loader(pathIn.getText());
            container.dispose();

         }
      });



      setPreferredSize(new Dimension(400, 150));

      getContentPane().setLayout(new BorderLayout());

      getContentPane().add(menu, BorderLayout.CENTER);

      getContentPane().add(bPane, BorderLayout.PAGE_END);
      menu.setBorder(new EmptyBorder(10, 10, 10, 10));
      menu.setLayout(new GridLayout(2, 1, 1, 1));
      menu.add(dataPath);
      menu.add(pathIn);
      bPane.add(submit);

      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      pack();

   }

   public void addMenu(Frame parent, Menus container)
   {
      menu = new JPanel();
      bPane = new JPanel();
      id = new JLabel("ID");
      idIn = new JTextField();
      lastName = new JLabel("Last Name");
      lastIn = new JTextField();
      firstName = new JLabel("First Name");
      firstIn = new JTextField();
      type = new JLabel("Vaccine Type");
      typeIn = new JTextField();
      date = new JLabel("Vaccination Date");
      dateIn = new JTextField();
      location = new JLabel("Location");
      locationIn = new JTextField();
      submit = new JButton("submit");
      submit.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            String [] update = {idIn.getText(), lastIn.getText(), firstIn.getText(), typeIn.getText(), dateIn.getText(), locationIn.getText()};
            while(update[0].length() < 5)
            {
               update[0] = "0" + update[0];
            }
            if(parent.validinput(update))
            {   
               parent.updateTab(update);
               container.dispose();
            }
         }
      });



      setPreferredSize(new Dimension(400, 400));

      getContentPane().setLayout(new BorderLayout());

      getContentPane().add(menu, BorderLayout.CENTER);

      getContentPane().add(bPane, BorderLayout.PAGE_END);

      menu.setBorder(new EmptyBorder(10, 10, 10, 10));
      menu.setLayout(new GridLayout(6, 2, 1, 1));
      menu.add(id);
      menu.add(idIn);
      menu.add(lastName);
      menu.add(lastIn);
      menu.add(firstName);
      menu.add(firstIn);
      menu.add(type);
      menu.add(typeIn);
      menu.add(date);
      menu.add(dateIn);
      menu.add(location);
      menu.add(locationIn);
      bPane.add(submit);
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      pack();
   }
}
