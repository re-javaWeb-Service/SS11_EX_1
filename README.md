Dựa trên mã nguồn hiện tại và các quy tắc nghiệp vụ, hệ thống đang gặp 2 lỗi logic nghiêm trọng dẫn đến việc tính sai phí:

1. Lỗi tính phí đối với cân nặng lẻ (ví dụ: 1.5kg):

Nguyên nhân: Mã nguồn đang sử dụng hàm Math.floor(weightKg - 1) để tính phần cân nặng vượt mức. Hàm Math.floor() sẽ làm tròn xuống số nguyên gần nhất.

Ví dụ: Với 1.5kg, công thức trở thành Math.floor(1.5 - 1) = Math.floor(0.5) = 0. Dẫn đến hệ thống không cộng thêm 10.000 VND cho 0.5kg lẻ này.

Cách khắc phục: Quy tắc yêu cầu "phân số của kg" cũng phải tính thêm tiền, do đó cần phải làm tròn lên bằng hàm Math.ceil(weightKg - 1).

2. Lỗi tính phí khoảng cách tại các ngưỡng biên (ví dụ: 49km vs 50km):

Nguyên nhân: Hệ thống đang nhân toàn bộ khoảng cách với một đơn giá cố định tùy theo mức (distanceKm * 5000 hoặc distanceKm * 4000). Điều này tạo ra sự vô lý tại các điểm chuyển giao.

Ví dụ: * Khách hàng gửi 49km: phí là 49 * 5000 = 245.000 VND.

Khách hàng gửi 50km: phí rơi vào nhánh else nên là 50 * 4000 = 200.000 VND.
(Gửi xa hơn lại có phí rẻ hơn).

Cách khắc phục: Phí khoảng cách cần được tính theo dạng lũy tiến (tương tự như tính tiền điện/taxi).

10km đầu tiên: 0 VND.

40km tiếp theo (từ km 10 đến km 50): (distanceKm - 10) * 5000 VND.

Từ km 50 trở đi: 200.000 VND (của 40km trước) + (distanceKm - 50) * 4000 VND."# SS11_EX_1" 
