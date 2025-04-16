package nhom.GUI.ADMIN_VIEW;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class AdminView extends JFrame {
    public JPanel jPanel_Tong, jPanel_Trai, jPanel_Phai;
    private JButton menuButton;
    private JMenuItem selectedMenuItem; // Lưu mục được chọn

    public AdminView() {
        this.init();
        this.setVisible(true);
    }

    private void init() {
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setTitle("Quản lý quán net");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Giao diện bên trái
        jPanel_Trai = new JPanel();
        jPanel_Trai.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jPanel_Trai.setPreferredSize(new Dimension(screenWidth / 5, screenHeight));
        jPanel_Trai.setBackground(Color.LIGHT_GRAY);

        // Giao diện bên trái ở trên
        JPanel jPanel_TraiTren = new JPanel();
        jPanel_TraiTren.setLayout(new GridLayout(3, 3));
        jPanel_TraiTren.setPreferredSize(new Dimension(305, 130));

        JLabel jLabel_xinChao = new JLabel("Người dùng:");
        JLabel jLabel_NguoiDung = new JLabel("Admin");
        JPanel jPanel_1 = new JPanel();
        JPanel jPanel_2 = new JPanel();
        JPanel jPanel_2_5 = new JPanel();
        JPanel jPanel_3 = new JPanel();
        JPanel jPanel_4 = new JPanel();
        JPanel jPanel_5 = new JPanel();

        ImageIcon imageIcon = new ImageIcon("D:\\YEAR_2\\k224\\Java_NC\\CK_JAVANC\\ck\\demo\\src\\main\\java\\nhom\\GUI\\ADMIN_VIEW\\Picture\\power.png");

        JButton imageButton = new JButton(imageIcon);
        imageButton.setBackground(Color.WHITE);
        imageButton.setBorderPainted(false);
        imageButton.setFocusPainted(false);
        imageButton.setContentAreaFilled(false);

        jPanel_TraiTren.add(jLabel_xinChao);
        jPanel_TraiTren.add(jLabel_NguoiDung);
        jPanel_TraiTren.add(jPanel_1);
        jPanel_TraiTren.add(jPanel_2);
        jPanel_TraiTren.add(jPanel_2_5);
        jPanel_TraiTren.add(imageButton);
        jPanel_TraiTren.add(jPanel_3);
        jPanel_TraiTren.add(jPanel_4);
        jPanel_TraiTren.add(jPanel_5);

        Font font_NguoiDung = new Font("Arial", Font.BOLD, 20);
        jLabel_NguoiDung.setFont(font_NguoiDung);

        jPanel_Trai.add(jPanel_TraiTren);

        // Giao diện cho trái dưới
        JPanel jPanel_TraiDuoi = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        jPanel_TraiDuoi.setPreferredSize(new Dimension(screenWidth / 5, screenHeight - 130));
        jPanel_TraiDuoi.setBackground(Color.LIGHT_GRAY);

        // Tạo nút để kích hoạt JPopupMenu
        menuButton = new JButton("Quản lý");
        menuButton.setPreferredSize(new Dimension(screenWidth / 5 - 10, 40));
        menuButton.setFont(new Font("Arial", Font.PLAIN, 16));
        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(true);
        menuButton.setBackground(Color.LIGHT_GRAY);

        // Hiệu ứng hover cho nút
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!menuButton.getBackground().equals(new Color(0, 139, 139))) {
                    menuButton.setBackground(new Color(0, 139, 139)); // Xanh nước biển
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!menuButton.getBackground().equals(new Color(0, 139, 139))) {
                    menuButton.setBackground(Color.LIGHT_GRAY); // Trở lại xám nhạt
                }
            }
        });

        // Tạo JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu("Quản lý");
        popupMenu.setPreferredSize(new Dimension(screenWidth / 5 - 10, 200));

        // Khi menu đóng, đặt lại màu của nút
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                menuButton.setBackground(new Color(0, 139, 139)); // Xanh khi menu mở
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                menuButton.setBackground(Color.LIGHT_GRAY); // Xám khi menu đóng
                if (selectedMenuItem != null) {
                    selectedMenuItem.setBackground(null);
                    selectedMenuItem.setOpaque(false);
                    selectedMenuItem = null;
                }
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                menuButton.setBackground(Color.LIGHT_GRAY); // Xám khi hủy
            }
        });

        // Tạo các JMenuItem
        JMenuItem quanLyMay = new JMenuItem("Quản lý máy");
        JMenuItem quanLyKhachHang = new JMenuItem("Quản lý khách hàng");
        JMenuItem quanLyDichVu = new JMenuItem("Quản lý dịch vụ");
        JMenuItem quanLyNhanVien = new JMenuItem("Quản lý nhân viên");
        JMenuItem quanLyHoaDon = new JMenuItem("Quản lý hóa đơn");

        // Đặt kích thước cho các JMenuItem
        quanLyMay.setPreferredSize(new Dimension(screenWidth / 5 - 10, 40));
        quanLyKhachHang.setPreferredSize(new Dimension(screenWidth / 5 - 10, 40));
        quanLyDichVu.setPreferredSize(new Dimension(screenWidth / 5 - 10, 40));
        quanLyNhanVien.setPreferredSize(new Dimension(screenWidth / 5 - 10, 40));
        quanLyHoaDon.setPreferredSize(new Dimension(screenWidth / 5 - 10, 40));

        // Hiệu ứng hover cho các JMenuItem
        MouseAdapter menuItemHover = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JMenuItem item = (JMenuItem) e.getSource();
                if (item != selectedMenuItem) {
                    item.setBackground(new Color(0, 139, 139)); // Xanh khi hover
                    item.setOpaque(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JMenuItem item = (JMenuItem) e.getSource();
                if (item != selectedMenuItem) {
                    item.setBackground(null); // Trở về mặc định
                    item.setOpaque(false);
                }
            }
        };

        quanLyMay.addMouseListener(menuItemHover);
        quanLyKhachHang.addMouseListener(menuItemHover);
        quanLyDichVu.addMouseListener(menuItemHover);
        quanLyNhanVien.addMouseListener(menuItemHover);
        quanLyHoaDon.addMouseListener(menuItemHover);

        // Thêm sự kiện để chọn mục và giữ menu mở
        ActionListener menuItemAction = e -> {
            JMenuItem item = (JMenuItem) e.getSource();
            // Đặt lại màu của mục được chọn trước đó
            if (selectedMenuItem != null) {
                selectedMenuItem.setBackground(null);
                selectedMenuItem.setOpaque(false);
            }
            // Cập nhật mục được chọn
            selectedMenuItem = item;
            selectedMenuItem.setBackground(Color.LIGHT_GRAY); // Xám khi chọn
            selectedMenuItem.setOpaque(true);
            // Giữ menu mở
            popupMenu.show(menuButton, 0, menuButton.getHeight());
        };

        quanLyMay.addActionListener(menuItemAction);
        quanLyKhachHang.addActionListener(menuItemAction);
        quanLyDichVu.addActionListener(menuItemAction);
        quanLyNhanVien.addActionListener(menuItemAction);
        quanLyHoaDon.addActionListener(menuItemAction);

        // Thêm các JMenuItem vào JPopupMenu
        popupMenu.add(quanLyMay);
        popupMenu.add(quanLyKhachHang);
        popupMenu.add(quanLyDichVu);
        popupMenu.add(quanLyNhanVien);
        popupMenu.add(quanLyHoaDon);

        // Thêm sự kiện cho nút để hiển thị JPopupMenu
        menuButton.addActionListener(e -> popupMenu.show(menuButton, 0, menuButton.getHeight()));

        // Thêm nút vào jPanel_TraiDuoi
        jPanel_TraiDuoi.add(menuButton);

        jPanel_Trai.add(jPanel_TraiDuoi);

        // Giao diện bên phải
        jPanel_Phai = new JPanel(new BorderLayout());
        jPanel_Phai.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        jPanel_Phai.setPreferredSize(new Dimension(4 * screenWidth / 5, screenHeight));
        jPanel_Phai.setBackground(Color.DARK_GRAY);

        // Thêm giao diện tổng
        this.add(jPanel_Trai, BorderLayout.WEST);
        this.add(jPanel_Phai, BorderLayout.CENTER);
    }
}