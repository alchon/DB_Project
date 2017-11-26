import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;
import java.io.File;
/**
 * @author 서현범 (2016003590)
 */

public class Hotel extends JFrame implements ActionListener{

    JPanel panel;
    // 메뉴바
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    JMenuItem menuItem = new JMenuItem("Open");
    // 제목
    JLabel mainlabel = new JLabel("호텔 관리 시스템",JLabel.CENTER);
    // 투숙예약
    JLabel booklabel = new JLabel("투숙예약",JLabel.CENTER);
    JLabel namelabel = new JLabel("고객명",JLabel.LEFT);
    JTextField nametext = new JTextField(6);
    JLabel datelabel = new JLabel("체크인(YYYYMMDD)",JLabel.LEFT);
    JTextField datetext = new JTextField(6);
    JLabel daylabel = new JLabel("박",JLabel.LEFT);
    JComboBox<String> daybox;
    JLabel roomlabel = new JLabel("객실",JLabel.LEFT);
    JComboBox<String> roombox;
    JButton registerbtn = new JButton("예약 등록/변경");
    JButton cancelbtn = new JButton("예약 취소");
    // 객실 예약 현황
    JLabel statuslabel = new JLabel("객실 예약 현황",JLabel.CENTER);
    JLabel datelabel2;
    JTextField[] room = new JTextField[20];
    // 등록/조회
    JLabel searchlabel = new JLabel("등록/조회",JLabel.CENTER);
    JTabbedPane tpane = new JTabbedPane();
    // 고객
    JPanel customer_panel = new JPanel();
    JLabel customer_namelabel = new JLabel("고객명", JLabel.LEFT);
    JTextField customer_nametext = new JTextField(6);
    JButton customer_registerbtn = new JButton("회원가입");
    JButton customer_searchbtn = new JButton("조회");
    JTextArea customer_infoarea = new JTextArea();
    // 객실
    JPanel room_panel = new JPanel();
    JLabel roomlabel2 = new JLabel("객실", JLabel.LEFT);
    JComboBox<String> roombox2;
    JTextArea room_infoarea = new JTextArea();
    // 직원
    JPanel client_panel = new JPanel();
    JLabel client_namelabel = new JLabel("직원명", JLabel.LEFT);
    JTextField client_nametext = new JTextField(6);
    JButton client_registerbtn = new JButton("직원등록");
    JButton client_searchbtn = new JButton("조회");
    JTextArea client_infoarea = new JTextArea();
    // 파일 열기
    File selectedFile;
    // 등록/조회 고객 회원가입 버튼
    JFrame customer_fs = new JFrame("회원가입");
    JLabel new_customer_name = new JLabel("고객명", JLabel.LEFT);
    JTextField new_customer_nametext = new JTextField(6);
    JLabel new_customer_sex = new JLabel("성별", JLabel.LEFT);
    JComboBox<String> new_customer_sexbox;
    JLabel new_customer_address = new JLabel("주소",JLabel.LEFT);
    JComboBox<String> new_customer_addressbox;
    JLabel new_customer_phone = new JLabel("연락처",JLabel.LEFT);
    JTextField new_customer_phonetext = new JTextField(6);
    JButton new_customer_registerbtn = new JButton("가입신청");
    JButton new_customer_cancelbtn = new JButton("취소");
    // 등록/조회 직원 회원가입 버튼
    JFrame client_fs = new JFrame("회원가입");
    JLabel new_client_name = new JLabel("직원명",JLabel.LEFT);
    JTextField new_client_nametext = new JTextField(6);
    JLabel new_client_sex = new JLabel("성별", JLabel.LEFT);
    JComboBox<String> new_client_sexbox;
    JLabel new_client_address = new JLabel("주소",JLabel.LEFT);
    JComboBox<String> new_client_addressbox;
    JLabel new_client_phone = new JLabel("연락처", JLabel.LEFT);
    JTextField new_client_phonetext = new JTextField(6);
    JButton new_client_registerbtn = new JButton("직원등록");
    JButton new_client_cancelbtn = new JButton("취소");

    private Hotel() {
        setTitle("호텔 관리 시스템");
        setSize(900,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.setProperty("apple.laf.useScreenMenuBar", "true");
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
        menuBar.add(menu);
        menu.add(menuItem);
        menuBar.setBounds(0,0,900,30);
        menuItem.addActionListener(this);
        panel.add(menuBar);
    }

    private void AddTitle() {
        mainlabel.setBorder(new LineBorder(Color.BLACK,2));
        mainlabel.setFont(new Font("Sans Serif", Font.BOLD, 30));
        mainlabel.setBounds(200,40,500,50);
        panel.add(mainlabel);
    }

    private void AddBook() {
        booklabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        booklabel.setBounds(20,120,150,40);

        namelabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        namelabel.setBounds(65,180,150,30);
        nametext.setBounds(250,180,150,30);

        datelabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        datelabel.setBounds(65,220,150,30);
        datetext.setBounds(250,220,150,30);

        daylabel.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        daylabel.setBounds(65,260,150,30);

        String[] day = new String[31];
        for (int i=1; i<=31; i++) {
            day[i-1] = String.valueOf(i);
        }
        daybox = new JComboBox<String>(day);

        daybox.setBounds(250,260,150,30);

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

        roombox = new JComboBox<String>(room);
        roombox.setBounds(250,300,150,30);

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
        statuslabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        statuslabel.setBounds(450,120,150,40);

        SimpleDateFormat date = new SimpleDateFormat("(yyyy-MM-dd)",Locale.KOREA);
        Date currentTime = new Date();
        String Time = date.format(currentTime);
        datelabel2 = new JLabel(Time, JLabel.CENTER);
        datelabel2.setFont(new Font("Sans Serif", Font.BOLD, 20));
        datelabel2.setBounds(650,120,150,40);

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
        panel.add(datelabel2);
        for (int i=0; i<20; i++) {
            room[i].setEditable(false);
            room[i].setHorizontalAlignment(JTextField.CENTER);
            room[i].setFont(new Font("Sans Serif", Font.BOLD, 20));
            room[i].setBorder(new LineBorder(Color.BLACK));
            panel.add(room[i]);
        }
    }

    private void AddSearch() {
        searchlabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        searchlabel.setBounds(20,450,150,40);
        panel.add(searchlabel);

        customer_panel.setLayout(null);
        customer_namelabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        customer_namelabel.setBounds(40,40,50,40);
        customer_nametext.setBounds(120,40,150,40);
        customer_registerbtn.setBounds(60,110,100,30);
        customer_searchbtn.setBounds(170,110,100,30);
        customer_infoarea.setEditable(false);
        customer_infoarea.setBounds(320,20,400,150);
        customer_panel.add(customer_namelabel);
        customer_panel.add(customer_nametext);
        customer_panel.add(customer_registerbtn);
        customer_panel.add(customer_searchbtn);
        customer_panel.add(customer_infoarea);

        room_panel.setLayout(null);
        roomlabel2.setFont(new Font("Sans Serif", Font.BOLD, 15));
        roomlabel2.setBounds(40,40,50,40);
        String[] room = new String[20];
        int index = 0;
        for (int i=1; i<=2; i++) {
            for (int j=1; j<= 10; j++) {
                int tmp = (i*100) + j;
                room[index] = String.valueOf(tmp);
                index ++;
            }
        }
        roombox2 = new JComboBox<String>(room);
        roombox2.setBounds(120,40,150,40);
        room_infoarea.setEditable(false);
        room_infoarea.setBounds(320,20,400,150);
        room_panel.add(roomlabel2);
        room_panel.add(roombox2);
        room_panel.add(room_infoarea);

        client_panel.setLayout(null);
        client_namelabel.setFont(new Font("Sans Serif", Font.BOLD, 15));
        client_namelabel.setBounds(40,40,50,40);
        client_nametext.setBounds(120,40,150,40);
        client_registerbtn.setBounds(60,110,100,30);
        client_searchbtn.setBounds(170,110,100,30);
        client_infoarea.setEditable(false);
        client_infoarea.setBounds(320,20,400,150);
        client_panel.add(client_namelabel);
        client_panel.add(client_nametext);
        client_panel.add(client_registerbtn);
        client_panel.add(client_searchbtn);
        client_panel.add(client_infoarea);

        customer_registerbtn.addActionListener(this);
        client_registerbtn.addActionListener(this);

        tpane.add("고객",customer_panel);
        tpane.add("객실", room_panel);
        tpane.add("직원", client_panel);
        tpane.setBounds(60,500,780,250);
        panel.add(tpane);
    }

    private void new_customer() {
        customer_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                customer_fs.setVisible(false);
                customer_fs.dispose();
            }
        });
        customer_fs.setLayout(null);
        Container c = customer_fs.getContentPane();
        new_customer_name.setBounds(60, 60, 150, 30);
        new_customer_name.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_customer_nametext.setBounds(140, 60, 150, 30);
        new_customer_sex.setBounds(60, 120, 150, 30);
        new_customer_sex.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        String sex[] = new String[2];
        sex[0] = "남";
        sex[1] = "여";
        new_customer_sexbox = new JComboBox<String>(sex);
        new_customer_sexbox.setBounds(140, 120, 150, 30);
        new_customer_address.setBounds(60, 180, 150, 30);
        new_customer_address.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        String address[] = new String [17];
        address[0] = "서울";
        address[1] = "경기";
        address[2] = "인천";
        address[3] = "부산";
        address[4] = "대구";
        address[5] = "대전";
        address[6] = "경남";
        address[7] = "전남";
        address[8] = "충남";
        address[9] = "광주";
        address[10] = "울산";
        address[11] = "경북";
        address[12] = "전북";
        address[13] = "충북";
        address[14] = "강원";
        address[15] = "제주";
        address[16] = "세종";
        new_customer_addressbox = new JComboBox<String>(address);
        new_customer_addressbox.setBounds(140, 180, 150,30);
        new_customer_phone.setBounds(60, 240, 150, 30);
        new_customer_phone.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_customer_phonetext.setBounds(140, 240, 150, 30);
        new_customer_registerbtn.setBounds(50, 300, 120, 30);
        new_customer_cancelbtn.setBounds(200, 300, 120, 30);
        c.add(new_customer_name);
        c.add(new_customer_nametext);
        c.add(new_customer_sex);
        c.add(new_customer_sexbox);
        c.add(new_customer_address);
        c.add(new_customer_addressbox);
        c.add(new_customer_phone);
        c.add(new_customer_phonetext);
        c.add(new_customer_registerbtn);
        c.add(new_customer_cancelbtn);

        new_customer_cancelbtn.addActionListener(this);

        customer_fs.setSize(400,400);
        customer_fs.setLocation(300,200);
        customer_fs.setVisible(true);
    }

    private void new_client() {
        client_fs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client_fs.setVisible(false);
                client_fs.dispose();
            }
        });
        client_fs.setLayout(null);
        Container c = client_fs.getContentPane();
        new_client_name.setBounds(60, 60, 150, 30);
        new_client_name.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_client_nametext.setBounds(140, 60, 150, 30);
        new_client_sex.setBounds(60, 120, 150, 30);
        new_client_sex.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        String sex[] = new String[2];
        sex[0] = "남";
        sex[1] = "여";
        new_client_sexbox = new JComboBox<String>(sex);
        new_client_sexbox.setBounds(140, 120, 150, 30);
        new_client_address.setBounds(60, 180, 150, 30);
        new_client_address.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        String address[] = new String [17];
        address[0] = "서울";
        address[1] = "경기";
        address[2] = "인천";
        address[3] = "부산";
        address[4] = "대구";
        address[5] = "대전";
        address[6] = "경남";
        address[7] = "전남";
        address[8] = "충남";
        address[9] = "광주";
        address[10] = "울산";
        address[11] = "경북";
        address[12] = "전북";
        address[13] = "충북";
        address[14] = "강원";
        address[15] = "제주";
        address[16] = "세종";
        new_client_addressbox = new JComboBox<String>(address);
        new_client_addressbox.setBounds(140, 180, 150,30);
        new_client_phone.setBounds(60, 240, 150, 30);
        new_client_phone.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        new_client_phonetext.setBounds(140, 240, 150, 30);
        new_client_registerbtn.setBounds(50, 300, 120, 30);
        new_client_cancelbtn.setBounds(200, 300, 120, 30);
        c.add(new_client_name);
        c.add(new_client_nametext);
        c.add(new_client_sex);
        c.add(new_client_sexbox);
        c.add(new_client_address);
        c.add(new_client_addressbox);
        c.add(new_client_phone);
        c.add(new_client_phonetext);
        c.add(new_client_registerbtn);
        c.add(new_client_cancelbtn);

        new_client_cancelbtn.addActionListener(this);

        client_fs.setSize(400,400);
        client_fs.setLocation(300,200);
        client_fs.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuItem) {
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = chooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
            }
        }
        if(e.getSource().equals(customer_registerbtn))
            new_customer();
        if(e.getSource().equals(client_registerbtn))
            new_client();
        if(e.getSource().equals(new_customer_cancelbtn)){
            customer_fs.setVisible(false);
            customer_fs.dispose();
        }
        if(e.getSource().equals(new_client_cancelbtn)) {
            client_fs.setVisible(false);
            client_fs.dispose();
        }
    }

    public static void main(String[] args) {
        new Hotel();
    }
}
