import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;

/**
 * @author Seo Hyeon-Beom (2016003590)
 */

public class Hotel extends JFrame implements ActionListener{

    JPanel panel;

    private Hotel() {
        setTitle("호텔 관리 시스템");
        setSize(900,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        AddPanel();
        Addmenu();
        AddTitle();
        AddBook();
        AddStatus();
        AddSearch();
        add(panel);
        setVisible(true);
    }

    private void AddPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 900, 800);
    }

    private void Addmenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
    }

    private void AddTitle() {
        JLabel label = new JLabel("호텔 관리 시스템",JLabel.CENTER);
        label.setBorder(new LineBorder(Color.BLACK,2));
        label.setFont(new Font("Sans Serif", Font.BOLD, 30));
        label.setBounds(200,30,500,50);
        panel.add(label);
    }

    private void AddBook() {
        JLabel booklabel = new JLabel("투숙예약",JLabel.CENTER);
        booklabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        booklabel.setBounds(20,120,150,40);

        JLabel namelabel = new JLabel("고객명",JLabel.LEFT);
        JTextField nametext = new JTextField(6);
        namelabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        namelabel.setBounds(65,180,150,30);
        nametext.setBounds(250,180,150,30);

        JLabel datelabel = new JLabel("체크인(YYYYMMDD)",JLabel.LEFT);
        JTextField datetext = new JTextField(6);
        datelabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        datelabel.setBounds(65,220,150,30);
        datetext.setBounds(250,220,150,30);

        JLabel daylabel = new JLabel("박",JLabel.LEFT);
        daylabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        daylabel.setBounds(65,260,150,30);

        String[] day = new String[31];
        for (int i=1; i<=31; i++) {
            day[i-1] = String.valueOf(i);
        }

        JComboBox<String> daybox = new JComboBox<String>(day);
        daybox.setBounds(250,260,150,30);

        JLabel roomlabel = new JLabel("객실",JLabel.LEFT);
        roomlabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        roomlabel.setBounds(65,300,150,30);

        String[] room = new String[20];
        int index = 0;
        for (int i=1; i<=2; i++) {
            for (int j=1; j<= 10; j++) {
                int tmp = (i*100) + j;
                room[index] = String.valueOf(tmp);
                index ++;
            }
        }

        JComboBox<String> roombox = new JComboBox<String>(room);
        roombox.setBounds(250,300,150,30);

        JButton registerbtn = new JButton("예약 등록/변경");
        JButton cancelbtn = new JButton("예약 취소");

        registerbtn.setBounds(90,345,120,30);
        cancelbtn.setBounds(260,345,120,30);

        panel.add(booklabel);
        panel.add(namelabel);
        panel.add(datelabel);
        panel.add(datetext);
        panel.add(nametext);
        panel.add(daylabel);
        panel.add(daybox);
        panel.add(roomlabel);
        panel.add(roombox);
        panel.add(registerbtn);
        panel.add(cancelbtn);
    }

    private void AddStatus() {
        JLabel statuslabel = new JLabel("객실 예약 현황",JLabel.CENTER);
        statuslabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statuslabel.setBounds(450,120,150,40);

        SimpleDateFormat date = new SimpleDateFormat("(yyyy-MM-dd)",Locale.KOREA);
        Date currentTime = new Date();
        String Time = date.format(currentTime);
        JLabel datelabel = new JLabel(Time, JLabel.CENTER);
        datelabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        datelabel.setBounds(650,120,150,40);

        JTextField[] room = new JTextField[20];
        int index = 0;
        for (int i=1; i<=2; i++) {
            for (int j=1; j<= 10; j++) {
                int tmp = (i*100) + j;
                room[index] = new JTextField(String.valueOf(tmp));
                index ++;
            }
        }

        index = 0;
        for (int i=170; i<= 320; i+=50) {
            for (int j=470; j<= 730; j+= 65) {
                room[index].setBounds(j,i,60,45);
                index ++;
            }
        }

        panel.add(statuslabel);
        panel.add(datelabel);
        for (int i=0; i<20; i++) {
            room[i].setEditable(false);
            room[i].setHorizontalAlignment(JTextField.CENTER);
            room[i].setFont(new Font("Sans Serif", Font.BOLD, 20));
            room[i].setBorder(new LineBorder(Color.BLACK));
            panel.add(room[i]);
        }
    }

    private void AddSearch() {
        JLabel searchlabel = new JLabel("등록/조회",JLabel.CENTER);
        searchlabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        searchlabel.setBounds(20,450,150,40);
        panel.add(searchlabel);
        JTabbedPane tpane = new JTabbedPane();

        JPanel customer_panel = new JPanel();
        customer_panel.setLayout(null);
        JLabel customer_namelabel = new JLabel("고객명", JLabel.LEFT);
        customer_namelabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        customer_namelabel.setBounds(40,40,50,40);
        JTextField customer_nametext = new JTextField(6);
        customer_nametext.setBounds(120,40,150,40);
        JButton customer_registerbtn = new JButton("회원가입");
        customer_registerbtn.setBounds(60,110,100,30);
        JButton customer_searchbtn = new JButton("조회");
        customer_searchbtn.setBounds(170,110,100,30);
        JTextArea customer_infoarea = new JTextArea();
        customer_infoarea.setEditable(false);
        customer_infoarea.setBounds(320,20,400,150);
        customer_panel.add(customer_namelabel);
        customer_panel.add(customer_nametext);
        customer_panel.add(customer_registerbtn);
        customer_panel.add(customer_searchbtn);
        customer_panel.add(customer_infoarea);

        JPanel room_panel = new JPanel();
        room_panel.setLayout(null);
        JLabel roomlabel = new JLabel("객실", JLabel.LEFT);
        roomlabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        roomlabel.setBounds(40,40,50,40);
        String[] room = new String[20];
        int index = 0;
        for (int i=1; i<=2; i++) {
            for (int j=1; j<= 10; j++) {
                int tmp = (i*100) + j;
                room[index] = String.valueOf(tmp);
                index ++;
            }
        }
        JComboBox<String> roombox = new JComboBox<String>(room);
        roombox.setBounds(120,40,150,40);
        JTextArea room_infoarea = new JTextArea();
        room_infoarea.setEditable(false);
        room_infoarea.setBounds(320,20,400,150);
        room_panel.add(roomlabel);
        room_panel.add(roombox);
        room_panel.add(room_infoarea);

        JPanel client_panel = new JPanel();
        client_panel.setLayout(null);
        JLabel client_namelabel = new JLabel("직원명", JLabel.LEFT);
        client_namelabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        client_namelabel.setBounds(40,40,50,40);
        JTextField client_nametext = new JTextField(6);
        client_nametext.setBounds(120,40,150,40);
        JButton client_registerbtn = new JButton("직원등록");
        client_registerbtn.setBounds(60,110,100,30);
        JButton client_searchbtn = new JButton("조회");
        client_searchbtn.setBounds(170,110,100,30);
        JTextArea client_infoarea = new JTextArea();
        client_infoarea.setEditable(false);
        client_infoarea.setBounds(320,20,400,150);
        client_panel.add(client_namelabel);
        client_panel.add(client_nametext);
        client_panel.add(client_registerbtn);
        client_panel.add(client_searchbtn);
        client_panel.add(client_infoarea);

        tpane.add("고객",customer_panel);
        tpane.add("객실", room_panel);
        tpane.add("직원", client_panel);
        tpane.setBounds(60,500,780,250);
        panel.add(tpane);
    }

    public static void main(String[] args) {
        new Hotel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
