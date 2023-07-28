package android.developer.ecommerce.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "MyTeamProject.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //#CATEGORIES			ID - NAME - IMG_BANNER - DESCRIPTION
        //      Computer - 1
        //      Smartphone - 2
        //      Other - 3
        //  Hidden = ID * 10
        //  Deleted = 0
        String CATEGORIES = "CREATE TABLE CATEGORIES" +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "IMG_BANNER TEXT," +
                "DESCRIPTION TEXT" +
                ")";
        db.execSQL(CATEGORIES);

        //#PRODUCTS				ID - NAME - IMG_BANNER - DESCRIPTION - CONFIG - ORIGINAL_PRICE - PRICE - CATEGORIES_ID
        String PRODUCTS = "CREATE TABLE PRODUCTS" +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "IMG_BANNER TEXT," +
                "DESCRIPTION TEXT," +
                "CONFIG TEXT," +
                "ORIGINAL_PRICE REAL," +
                "PRICE REAL," +
                "" +
                "CATEGORIES_ID INTEGER," +
                "FOREIGN KEY (CATEGORIES_ID) REFERENCES CATEGORIES(ID)" +
                ")";
        db.execSQL(PRODUCTS);

        //#CUSTOMERS			ID - USERNAME - PASSWORD - NAME - PHONE_NUMBER - ADDRESS
        String CUSTOMERS = "CREATE TABLE CUSTOMERS" +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USERNAME TEXT UNIQUE," +
                "PASSWORD TEXT," +
                "NAME TEXT," +
                "PHONE_NUMBER TEXT," +
                "ADDRESS TEXT" +
                ")";
        db.execSQL(CUSTOMERS);

        //#MANAGERS				ID - EMAIL - PASSWORD - ADMIN - NAME
        //	ADMIN
        //      0 - FALSE
        //      1 - TRUE
        String MANAGERS = "CREATE TABLE MANAGERS" +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "EMAIL TEXT UNIQUE," +
                "PASSWORD TEXT," +
                "ADMIN INTEGER," +
                "NAME TEXT" +
                ")";
        db.execSQL(MANAGERS);

        //#ORDERS				ID - ORDER_DATE - RECEIVED_DATE - TOTAL - STATUS - CUSTOMERS_ID - MANAGERS_ID
        //  STATUS
        //      -1 - CHƯA ĐẶT HÀNG
        //      0 - CHỜ XÁC NHẬN
        //		1 - ĐANG GIAO
        //      10 - ĐÃ GIAO
        //      99 - ĐÃ HỦY
        String ORDERS = "CREATE TABLE ORDERS" +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ORDER_DATE TEXT," +
                "RECEIVED_DATE TEXT," +
                "TOTAL REAL," +
                "STATUS INTEGER DEFAULT -1," +
                "" +
                "CUSTOMERS_ID INTEGER," +
                "MANAGERS_ID INTEGER," +
                "FOREIGN KEY (CUSTOMERS_ID) REFERENCES CUSTOMERS(ID)," +
                "FOREIGN KEY (MANAGERS_ID) REFERENCES MANAGERS(ID)" +
                ")";
        db.execSQL(ORDERS);

        //#ORDER_DETAILS		ID - QUANTITY - TOTAL - PRODUCTS_ID - ORDERS_ID
        String ORDER_DETAILS = "CREATE TABLE ORDER_DETAILS" +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "QUANTITY INTEGER," +
                "TOTAL REAL," +
                "" +
                "PRODUCTS_ID INTEGER," +
                "ORDERS_ID INTEGER," +
                "FOREIGN KEY (PRODUCTS_ID) REFERENCES PRODUCTS(ID)," +
                "FOREIGN KEY (ORDERS_ID) REFERENCES ORDERS(ID)" +
                ")";
        db.execSQL(ORDER_DETAILS);

        //#DATA
        db.execSQL("INSERT INTO CATEGORIES (NAME , IMG_BANNER , DESCRIPTION) VALUES" +
                "('Computer','1ddg2ekVxRwRJLEUMaFWW5raL1A8KiC3c','Desktop , Laptop , Macbook...')," +
                "('Smart Phone','16LMlXA2nUCHe_XSeGu9TE3jwGfzHyilT','Android , IPhone...')," +
                "('Other','1GkhOugsGvT1JhHUlgIdOOXxHVFLXh3c8','Tivi , Smart Watch...')");
        db.execSQL("INSERT INTO CATEGORIES (ID , NAME) VALUES" +
                "(0,'Deleted')," +
                "(10,'Computer')," +
                "(20,'Smart Phone')," +
                "(30,'Other')");

        db.execSQL("INSERT INTO MANAGERS (EMAIL , PASSWORD , ADMIN , NAME ) VALUES" +
                "('admin@gmail.com' , 'admin' , 1 , 'Lê Mạnh Thái' )");


        //#PRODUCTS				ID - NAME - IMG_BANNER - DESCRIPTION - CONFIG - ORIGINAL_PRICE - PRICE - CATEGORIES_ID
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +
                //      #2 - Smart phone
                "('iPhone 14 Pro Max','https://cdn2.cellphones.com.vn/x/media/catalog/product/t/_/t_m_18.png'," +
                "'iPhone 14 Pro Max 1TB là phiên bản điện thoại cao cấp nhất mà Apple đã cho ra mắt tại sự kiện giới thiệu sản phẩm mới cho năm 2022. Được thừa hưởng mọi công nghệ hàng đầu thế giới nên máy hứa hẹn sẽ mang lại trải nghiệm sử dụng tốt nhất từ chơi game cho tới chụp ảnh, xứng đáng là chiếc điện thoại đáng mua nhất trên thị trường hiện tại." +
                "\nKiểu dáng thiết kế sang trọng và cao cấp" +
                "\nTrải nghiệm thích hơn trên màn hình lớn" +
                "\nChụp ảnh chất lượng cao như một nhiếp ảnh gia thực thụ" +
                "\nHiệu năng cực khủng'," +
                "'6GB / 1 TB" +
                "\nMàn hình : OLED6.1\"Super Retina XDR" +
                "\nHệ điều hành : iOS 16" +
                "\nCamera sau : Chính 48 MP & Phụ 12 MP, 12 MP" +
                "\nCamera trước : 12 MP" +
                "\nChip : Apple A16 Bionic" +
                "\nRAM : 6 GB" +
                "\nDung lượng lưu trữ : 1 TB" +
                "\nSIM : 1 Nano SIM & 1 eSIMHỗ trợ 5G" +
                "\nPin, Sạc : 4323 mAh20 W'," +
                "2000,2099,2" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('iPhone 14 Pro','https://cdn2.cellphones.com.vn/x/media/catalog/product/t/_/t_m_18.png'," +
                "'iPhone 14 Pro 1TB tiếp tục thể hiện sức hot của mình ngay sau khi ra mắt nhờ thiết kế mang những cải tiến tinh tế, hiệu năng bùng nổ cùng bộ vi xử lý A16 Bionic hoàn toàn mới những cải, sẵn sàng cân mọi tác vụ của người dùng." +
                "\nTinh tế đến từng đường nét" +
                "\nGiải trí thêm hoàn hảo cùng màn hình sắc nét" +
                "\nCamera cải tiến toàn diện'," +
                "'6GB / 1 TB" +
                "\nMàn hình : OLED6.1\"Super Retina XDR" +
                "\nHệ điều hành : iOS 16" +
                "\nCamera sau : Chính 48 MP & Phụ 12 MP, 12 MP" +
                "\nCamera trước : 12 MP" +
                "\nChip : Apple A16 Bionic" +
                "\nRAM : 6 GB" +
                "\nDung lượng lưu trữ : 1 TB" +
                "\nSIM : 1 Nano SIM & 1 eSIMHỗ trợ 5G" +
                "\nPin, Sạc : 3200 mAh20 W'," +
                "1700,1799,2" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('iPhone 14 Plus','https://alodidong.vn/storage/14xanh-600x600-6.png'," +
                "'iPhone 14 Plus 256GB vừa được Apple cho ra mắt với một diện mạo sang chảnh về thiết kế, vượt trội về hiệu năng và quay phim chụp ảnh chuyên nghiệp. Đây hứa hẹn sẽ là dòng sản phẩm mới nổi và sẽ thu hút được nhiều khách hàng săn đón trong thời gian tới." +
                "\nThiết kế sang trọng đầy tinh tế" +
                "\nKhông gian hiển thị rộng rãi và màu sắc trung thực" +
                "\nThỏa sức chụp ảnh như một nhiếp ảnh gia chuyên nghiệp" +
                "\nHiệu năng đỉnh cao'," +
                "'6 / 256GB" +
                "\nMàn hình : OLED6.7\"Super Retina XDR" +
                "\nHệ điều hành : iOS 16" +
                "\nCamera sau : 2 camera 12 MP" +
                "\nCamera trước : 12 MP" +
                "\nChip : Apple A15 Bionic" +
                "\nRAM : 6 GB" +
                "\nDung lượng lưu trữ : 256 GB" +
                "\nSIM : 1 Nano SIM & 1 eSIMHỗ trợ 5G" +
                "\nPin, Sạc : 4325 mAh20 W'," +
                "1000,1099,2" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +
                "('iPhone 14','https://shopdunk.com/images/thumbs/0009181_iphone-14-128gb.png'," +
                "'Mới đây thì tại sự kiện ra mắt sản phẩm mới thường niên đến từ nhà Apple thì chiếc điện thoại iPhone 14 256GB cũng đã chính thức lộ diện, thiết bị được nâng cấp toàn diện từ camera cho đến hiệu năng và hầu hết là những thông số hàng đầu trong giới smartphone." +
                "\nĐẳng cấp thiết kế dẫn đầu xu thế" +
                "\nTrang bị công nghệ màn hình tân tiến" +
                "\nHỗ trợ chụp ảnh quay phim chuẩn điện ảnh" +
                "\nVi xử lý mạnh mẽ đến từ nhà Apple'," +
                "'6 / 256GB" +
                "\nMàn hình : OLED6.1\"Super Retina XDR" +
                "\nHệ điều hành : iOS 16" +
                "\nCamera sau : 2 camera 12 MP" +
                "\nCamera trước : 12 MP" +
                "\nChip : Apple A15 Bionic" +
                "\nRAM : 6 GB" +
                "\nDung lượng lưu trữ : 256 GB" +
                "\nSIM : 1 Nano SIM & 1 eSIMHỗ trợ 5G" +
                "\nPin, Sạc : 3279 mAh20 W'," +
                "900,999,2" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('Samsung Galaxy Z Flip4','https://clickbuy.com.vn/uploads/2022/07/Z-Flip-Bora-Purple-640x640-1.png'," +
                "'Mới đây tại sự kiện Unpacked 2022 toàn cầu diễn ra ngày 10/08 thì chiếc Samsung Galaxy Z Flip4 512GB cũng đã chính thức lộ diện với một vẻ ngoài cuốn hút đầy bắt mắt. Máy được nâng cấp độ bền so với thế hệ cũ nhờ sử dụng nhiều vật liệu cao cấp để chế tạo, từ đó làm tăng mức độ uy tín giúp người dùng có thể an tâm trang bị và sử dụng trong thời gian dài." +
                "\nDiện mạo thời thượng hợp xu hướng" +
                "\nTrải nghiệm tốt hơn trên màn hình chất lượng" +
                "\nTích hợp bộ đôi camera chất lượng'," +
                "'8 / 512GB" +
                "\nMàn hình : Chính: Dynamic AMOLED 2X, Phụ: Super AMOLEDChính 6.7\" & Phụ 1.9\"Full HD+" +
                "\nHệ điều hành : Android 12" +
                "\nCamera sau : 2 camera 12 MP" +
                "\nCamera trước : 10 MP" +
                "\nChip : Snapdragon 8+ Gen 1" +
                "\nRAM : 8 GB" +
                "\nDung lượng lưu trữ : 512 GB" +
                "\nSIM : 1 Nano SIM & 1 eSIMHỗ trợ 5G" +
                "\nPin, Sạc : 3700 mAh25 W'," +
                "900,999,2" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('Samsung Galaxy Z Fold4','https://img.vn.my-best.com/contents/456373d06075f68222d79e2650c7bc43.png?ixlib=rails-4.3.1&q=70&lossless=0&w=1200&h=900&fit=crop&s=229ed95b7618e93d6a8b8200862b55cb'," +
                "'Samsung Galaxy Z Fold4 512G có lẽ là cái tên dành được nhiều sự chú ý đến từ sự kiện Unpacked thường niên của Samsung, bởi máy sở hữu màn hình lớn cùng cơ chế gấp gọn giúp người dùng có thể dễ dàng mang theo. Cùng với đó là sự nâng cấp về hiệu năng và phần mềm giúp máy xử lý tốt hầu hết mọi tác vụ bạn cần từ làm việc đến giải trí." +
                "\nVẻ ngoài thời thượng chuẩn chỉnh flagship" +
                "\nNhiếp ảnh chuyên nghiệp với bộ 3 camera cao cấp" +
                "\nMàn hình rộng lớn cùng màu sắc hiển thị sinh động'," +
                "'12 / 512GB" +
                "\nMàn hình : Dynamic AMOLED 2XChính 7.6\" & Phụ 6.2\"Quad HD+ (2K+)" +
                "\nHệ điều hành : Android 12" +
                "\nCamera sau : Chính 50 MP & Phụ 12 MP, 10 MP" +
                "\nCamera trước : 10 MP & 4 MP" +
                "\nChip : Snapdragon 8+ Gen 1" +
                "\nRAM : 12 GB" +
                "\nDung lượng lưu trữ : 512 GB" +
                "\nSIM : 1 Nano SIM & 1 eSIMHỗ trợ 5G" +
                "\nPin, Sạc : 4400 mAh25 W'," +
                "1600,1699,2" +
                ")");
        //      #3 - Other
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +
                "('Apple Watch Series 7 GPS 41mm','https://cdn.tgdd.vn/Products/Images/7077/249906/s16/apple-watch-s7-gps-41mm-vang-nhat-650x650.png'," +
                "'Kiểu dáng hiện đại, thời thượng" +
                "\nHệ điều hành WatchOS 8 với những cải tiến nổi bật" +
                "\nMàn hình OLED luôn luôn hiển thị" +
                "\nSử dụng lên đến 1.5 ngày cho một lần sạc" +
                "\nDễ dàng kết nối với điện thoại qua Bluetooth" +
                "\nTheo dõi sức khỏe mọi lúc mọi nơi'," +
                "'OLED1.61 inch" +
                "\nMàn hình : OLED1.61 inch" +
                "\nThời lượng pin : Khoảng 1.5 ngày (ở chế độ tiết kiệm pin)Khoảng 18 giờ (ở chế độ sử dụng thông thường)" +
                "\nKết nối với hệ điều hành : iPhone 8 trở lên với iOS phiên bản mới nhất" +
                "\nMặt : Ion-X strengthened glass41 mm" +
                "\nTính năng cho sức khỏe : Đo nhịp timTính lượng calories tiêu thụChế độ luyện tậpTính quãng đường chạyĐếm số bước chânĐiện tâm đồĐo nồng độ oxy (SpO2)Theo dõi giấc ngủ" +
                "\nHãng : Apple.'," +
                "250,299,3" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('Apple Watch S8 GPS 41mm','https://bachlongmobile.com/media/catalog/product/cache/2/image/040ec09b1e35df139433887a97daa66f/a/p/apple-watch-s8-41mm-trang-kem-thumbtz-650x650.png'," +
                "'Tại sự kiện Far Out 2022 ngoài iPhone Series thì Apple còn giới thiệu đến giới công nghệ Apple Watch Series 8. Thu hút được sự chú ý của công chúng, vấn đề hiệu năng của thiết bị này cũng được quan tâm đặc biệt. Trong bài viết này mình sẽ đưa ra đánh giá cho sản phẩm Apple Watch S8 GPS 41mm sau hơn một thời trải nghiệm." +
                "\nSở hữu phong cách thời thượng, vẻ ngoài chưa có nhiều sự đổi mới" +
                "\nHiệu năng thực tế và khả năng định vị của sản phẩm" +
                "\nCác tính năng chăm sóc sức khỏe được nâng cấp, hỗ trợ rèn luyện cơ thể tốt hơn'," +
                "'OLED1.9 inch" +
                "\nMàn hình : OLED1.9 inch" +
                "\nThời lượng pin : Khoảng 1.5 ngày (ở chế độ tiết kiệm pin)Khoảng 18 giờ (ở chế độ sử dụng thông thường)" +
                "\nKết nối với hệ điều hành : iPhone 8 trở lên với iOS phiên bản mới nhất" +
                "\nMặt : Ion-X strengthened glass41 mm" +
                "\nTính năng cho sức khỏe : Đo nhịp timTính lượng calories tiêu thụChế độ luyện tậpTính quãng đường chạyĐếm số bước chânĐiện tâm đồĐo nồng độ oxy (SpO2)Theo dõi giấc ngủ" +
                "\nHãng : Apple.'," +
                "400,419,3" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('Samsung Galaxy Watch5 LTE 44mm Đen','https://cdn2.cellphones.com.vn/x/media/catalog/product/9/_/9_41_4.png'," +
                "'Trong sự kiện ra mắt sản phẩm mới vào tháng 8/2022, Samsung đã trình làng mẫu smartwatch mới nhất của mình mang tên Samsung Galaxy Watch5 LTE 44 mm với nhiều nâng cấp đáng giá, hứa hẹn làm hài lòng các tín đồ yêu công nghệ, phục vụ cuộc sống với nhiều tiện ích thông minh." +
                "\nThiết kế năng động nhưng không kém phần cao cấp" +
                "\nCông nghệ đột phá, sống khoẻ toàn diện" +
                "\nHuấn luyện viên ảo chuyên nghiệp trên cổ tay'," +
                "'SUPER AMOLED1.4 inch" +
                "\nMàn hình : SUPER AMOLED1.4 inch" +
                "\nThời lượng pin : Khoảng 1.6 ngày" +
                "\nKết nối với hệ điều hành : Android 8.0 trở lên dùng Google Mobile Service" +
                "\nMặt : Kính Sapphire44 mm" +
                "\nTính năng cho sức khỏe : Đo huyết áp (chỉ hỗ trợ khi kết nối với điện thoại Samsung)Điện tâm đồ (chỉ hỗ trợ khi kết nối với điện thoại Samsung)Đo nhịp timTính lượng calories tiêu thụChế độ luyện tậpTheo dõi mức độ stressĐếm số bước chânĐo nồng độ oxy (SpO2)Theo dõi giấc ngủ" +
                "\nHãng : Samsung.'," +
                "270,299,3" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('Garmin Forerunner 955 dây silicone','https://paradoxfwc.com/wp-content/uploads/2022/06/955r.png'," +
                "'Sau sự thành công của dòng Forerunner 945, Garmin đã cho ra mắt phiên bản nâng cấp với tên gọi Garmin Forerunner 955. Sở hữu tất cả những tính năng phục vụ thể thao chuyên nghiệp cùng với việc được nâng cấp màn hình cảm ứng, chip GPS đa băng tần,... hứa hẹn sẽ làm hài lòng mọi tín đồ yêu thể thao của nhà Garmin." +
                "\nNgoại hình được làm mới" +
                "\nTrang bị thêm những tính năng cao cấp" +
                "\nHuấn luyện viên thể thao chuyên nghiệp" +
                "\nĐo các chỉ số sức khỏe, thể chất'," +
                "'MIP1.3 inch" +
                "\nMàn hình : MIP1.3 inch" +
                "\nThời lượng pin : Khoảng 15 ngày (ở chế độ đồng hồ thông minh)Khoảng 42 giờ (Chế độ chỉ có GPS không có nhạc)" +
                "\nKết nối với hệ điều hành : iOS 14 trở lênAndroid 7.0 trở lên" +
                "\nMặt : Kính cường lực Gorilla Glass DX46.5 mm" +
                "\nTính năng cho sức khỏe : Đo huyết áp (chỉ hỗ trợ khi kết nối với điện thoại Samsung)Điện tâm đồ (chỉ hỗ trợ khi kết nối với điện thoại Samsung)Đo nhịp timTính lượng calories tiêu thụChế độ luyện tậpTheo dõi mức độ stressĐếm số bước chânĐo nồng độ oxy (SpO2)Theo dõi giấc ngủ" +
                "\nHãng : Garmin.'," +
                "450,499,3" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('Smart Tivi Samsung 4K Crystal UHD 65 inch UA65AU7200','https://dienmaykhaitri.com/files/sanpham/653/1/png/smart-tivi-samsung-4k-crystal-uhd-ua50tu7000-50-inch.png'," +
                "'Sang trọng, tinh tế với màn hình mỏng không viền 3 cạnh" +
                "\nMang đến những khung hình sắc nét, chi tiết cao với độ phân giải Ultra HD 4K" +
                "\nNâng cấp độ tương phản, tăng cường chiều sâu và độ chân thực qua công nghệ HDR10+ và Contrast Enhancer" +
                "\nBộ xử lý Crystal 4K hiển thị các màu sắc tinh khiết và rõ ràng hơn" +
                "\nDải màu rộng với hình ảnh thật như cuộc sống qua công nghệ Crystal Display và UHD Dimming'," +
                "'Ultra HD 4K 65inch" +
                "\nLoại tivi : Smart Tivi65 inch4K" +
                "\nHệ điều hành : Tizen OS 6.0" +
                "\nỨng dụng phổ biến : Clip TVFPT PlayGalaxy Play (Fim+)MP3 ZingMyTVNetflixPOPS KidsSpotifyTrình duyệt webVieONYouTube" +
                "\nCông nghệ hình ảnh : Chiếu hình từ điện thoại lên TV" +
                "\nKích thước : Ngang 144.9 cm - Cao 90.6 cm - Dày 28.2 cm" +
                "\nHãng : Samsung.'," +
                "480,509,3" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('Google Tivi Sony 4K 65 inch XR-65X90K','https://promotion.sony.com.vn/Data/Sites/1/Product/5738/tivi-sony-bravia-43x80l-main.png'," +
                "'Sang trọng, tinh tế với màn hình mỏng không viền 3 cạnh" +
                "\nMang đến những khung hình sắc nét, chi tiết cao với độ phân giải Ultra HD 4K" +
                "\nNâng cấp độ tương phản, tăng cường chiều sâu và độ chân thực qua công nghệ HDR10+ và Contrast Enhancer" +
                "\nBộ xử lý Crystal 4K hiển thị các màu sắc tinh khiết và rõ ràng hơn" +
                "\nDải màu rộng với hình ảnh thật như cuộc sống qua công nghệ Crystal Display và UHD Dimming'," +
                "'Ultra HD 4K 65inch" +
                "\nLoại tivi : Smart Tivi65 inch4K" +
                "\nHệ điều hành : Google TV" +
                "\nỨng dụng phổ biến : Clip TVFPT PlayGalaxy Play (Fim+)NetflixVieONVTVcab ONYouTube" +
                "\nCông nghệ hình ảnh : Google Assistant có tiếng ViệtTìm kiếm giọng nói trên YouTube bằng tiếng Việt" +
                "\nKích thước : Ngang 145.2 cm - Cao 86.3 cm - Dày 33.1 cm" +
                "\nHãng : Sony.'," +
                "440,499,3" +
                ")");
        //        1 - Computer
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +
                "('MacBook Pro 14 M1 Pro 2021','https://cdn.hoanghamobile.com/i/preview/Uploads/2023/02/10/s0.png'," +
                "'Apple đã giới thiệu MacBook Pro 14 inch vào tháng 10/2021, mang một diện mạo mới cùng bộ vi xử lý do hãng tự phát triển, không chỉ cho hiệu năng vượt trội mà còn sở hữu màn hình với khả năng hiển thị thực sự ấn tượng, khiến mình mê mẩn khi cầm trên tay trải nghiệm." +
                "\nSức mạnh hiệu năng đỉnh cao hơn bao giờ hết" +
                "\nNgoại hình đẳng cấp, thể hiện chất “tôi” riêng của bạn" +
                "\nCác công nghệ màn hình cho chất lượng giải trí trên cả tuyệt vời'," +
                "'16 / 512GB" +
                "\nCPU : Apple M1 Pro 200GB/s" +
                "\nRAM : 16 GB" +
                "\nỔ cứng : 512 GB SSD" +
                "\nMàn hình : 14.2\"Liquid Retina XDR display (3024 x 1964)120Hz" +
                "\nCard màn hình : Card tích hợp14 nhân GPU" +
                "\nCổng kết nối : HDMI3 x Thunderbolt 4 USB-CJack tai nghe 3.5 mm" +
                "\nĐặc biệt : Có đèn bàn phím" +
                "\nHệ điều hành : Mac OS" +
                "\nThiết kế : Vỏ kim loại nguyên khối" +
                "\nKích thước, khối lượng : Dài 312.6 mm - Rộng 221.2 mm - Dày 15.5 mm - Nặng 1.6 kg" +
                "\nThời điểm ra mắt : 10/2021'," +
                "1500,1799,1" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +
                "('Lenovo Gaming Legion 5','https://trungtran.vn/upload_images/images/products/lenovo-legion/large/Lenovo_Legion_5_Pro_16IAH7H_CT1_03.png'," +
                "'Với phong cách thiết kế độc đáo cùng card đồ họa NVIDIA GeForce mạnh mẽ, laptop Lenovo Gaming Legion 5 15ACH6 (82JW00KLVN) đáp ứng tốt mọi tác vụ từ học tập, văn phòng nhẹ nhàng đến đồ họa, kỹ thuật chuyên sâu.'," +
                "'8 / 512GB" +
                "\nCPU : Ryzen 55600H3.3GHz" +
                "\nRAM : 8 GBDDR4 2 khe (1 khe 8 GB + 1 khe rời)3200 MHz" +
                "\nỔ cứng : 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 1 TB (2280) / 512 GB (2242))Hỗ trợ thêm 1 khe cắm SSD M.2 PCIe mở rộng (nâng cấp tối đa 1 TB)" +
                "\nMàn hình : 15.6\"Full HD (1920 x 1080) 165Hz" +
                "\nCard màn hình : Card rờiRTX 3050 4GB" +
                "\nCổng kết nối : HDMILAN (RJ45)2 x USB Type-C1 x USB 3.2 (Always on)Jack tai nghe 3.5 mm3 x USB 3.2" +
                "\nĐặc biệt : Có đèn bàn phím" +
                "\nHệ điều hành : Windows 11 Home SL" +
                "\nThiết kế : Vỏ nhựa" +
                "\nKích thước, khối lượng : Dài 362.56 mm - Rộng 260.61 mm - Dày 25.75 mm - Nặng 2.4 kg" +
                "\nThời điểm ra mắt : 2021'," +
                "900,1049,1" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('Dell XPS 13 9310','https://laptopvang.com/wp-content/uploads/2021/10/laptop-dell-xps-13-2-in-1-13-inch-laptopvang-4-600x571.png'," +
                "'Laptop Dell XPS 13 9310 i5 1135G7 (70273578) sở hữu thiết kế hiện đại với màu bạc thời thượng cùng hiệu năng mạnh mẽ, xứng danh bạn đồng hành đắc lực trên mọi mặt trận.'," +
                "'8 / 512GB" +
                "\nCPU : i51135G72.4GHz" +
                "\nRAM : 8 GBLPDDR4X (Onboard)4267 MHz" +
                "\nỔ cứng : 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 2 TB)" +
                "\nMàn hình : 13.4\"Full HD+ (1920 x 1200)" +
                "\nCard màn hình : Card tích hợpIntel Iris Xe" +
                "\nCổng kết nối : 2 x Thunderbolt 4 USB-CJack tai nghe 3.5 mm" +
                "\nĐặc biệt : Có đèn bàn phím" +
                "\nHệ điều hành : Windows 11 Home SL + Office Home & Student vĩnh viễn" +
                "\nThiết kế : Vỏ kim loại" +
                "\nKích thước, khối lượng : Dài 295 mm - Rộng 198 mm - Dày 14.8 mm - Nặng 1.2 kg" +
                "\nThời điểm ra mắt : 2021'," +
                "1500,1599,1" +
                ")");
        db.execSQL("INSERT INTO PRODUCTS (NAME , IMG_BANNER , DESCRIPTION , CONFIG ,ORIGINAL_PRICE, PRICE,CATEGORIES_ID) VALUES" +

                "('LG gram 2022','https://cdn2.cellphones.com.vn/x/media/catalog/product/l/g/lg-gram-style-2023-16z90rs-g-ah5.png'," +
                "'Laptop LG gram 2022 i7 1260P (14Z90Q-G.AH75A5) là thế hệ laptop cao cấp - sang trọng mới được LG cho ra mắt với lối thiết kế hiện đại cùng hiệu năng mạnh mẽ, xứng danh trợ thủ đắc lực cho mọi đối tượng người dùng.'," +
                "'8 / 512GB" +
                "\nCPU : i71260P2.1GHz" +
                "\nRAM : 16 GBLPDDR5 (8 GB Onboard + 8 GB Onboard)5200 MHz" +
                "\nỔ cứng : 512 GB SSD NVMe PCIeHỗ trợ thêm 1 khe cắm SSD M.2 PCIe mở rộng" +
                "\nMàn hình : 16\"WQXGA (2560 x 1600)" +
                "\nCard màn hình : Card tích hợpIntel Iris Xe" +
                "\nCổng kết nối : HDMIJack tai nghe 3.5 mm2 x USB 3.22 x USB Type C (có USB PD, cổng hiển thị, Thunderbolt 4)" +
                "\nĐặc biệt : Có đèn bàn phím" +
                "\nHệ điều hành : Windows 11 Home SL" +
                "\nThiết kế : Vỏ kim loại" +
                "\nKích thước, khối lượng : Dài 354.5 mm - Rộng 242.1 mm - Dày 16.8 mm - Nặng 1.285 kg" +
                "\nThời điểm ra mắt : 2022'," +
                "1800,1999,1" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
