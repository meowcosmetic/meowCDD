# Optional Type Fix

## 📋 Vấn đề đã được sửa

**Lỗi:** `java: incompatible types: java.util.Optional<com.meowcdd.document.ProgressReport> cannot be converted to com.meowcdd.document.ProgressReport`

## 🔍 Nguyên nhân

Repository method `findFirstByChildExternalIdOrderByReportDateDesc()` trả về `Optional<ProgressReport>` nhưng service method cố gắng return trực tiếp `ProgressReport`.

## 🛠️ Cách sửa

### Trước (Lỗi):
```java
public ProgressReport getLatestProgressReportByChildId(String childExternalId) {
    log.info("Getting latest progress report for child: {}", childExternalId);
    return progressReportRepository.findFirstByChildExternalIdOrderByReportDateDesc(childExternalId);
}
```

### Sau (Đã sửa):
```java
public ProgressReport getLatestProgressReportByChildId(String childExternalId) {
    log.info("Getting latest progress report for child: {}", childExternalId);
    return progressReportRepository.findFirstByChildExternalIdOrderByReportDateDesc(childExternalId)
            .orElse(null);
}
```

## 📋 Các lựa chọn xử lý Optional

### 1. Return null nếu không tìm thấy:
```java
.orElse(null)
```

### 2. Throw exception nếu không tìm thấy:
```java
.orElseThrow(() -> new RuntimeException("Not found"))
```

### 3. Return default value:
```java
.orElse(new ProgressReport())
```

### 4. Return Optional (thay đổi return type):
```java
public Optional<ProgressReport> getLatestProgressReportByChildId(String childExternalId) {
    return progressReportRepository.findFirstByChildExternalIdOrderByReportDateDesc(childExternalId);
}
```

## 🧪 Test

Chạy script test để kiểm tra:
```bash
chmod +x test_optional_fix.sh
./test_optional_fix.sh
```

## 📝 Lưu ý

- **Spring Data MongoDB:** Các method `findFirstBy...` thường trả về `Optional<T>`
- **Spring Data MongoDB:** Các method `findTopBy...` thường trả về `List<T>`
- **Luôn kiểm tra:** Return type của repository method trước khi sử dụng
- **Xử lý null:** Cần xử lý trường hợp không tìm thấy dữ liệu

## 🔍 Các method tương tự cần chú ý

- `findFirstBy...` → Trả về `Optional<T>`
- `findTopBy...` → Trả về `List<T>`
- `findBy...` → Trả về `List<T>` hoặc `Optional<T>` (tùy theo số lượng kết quả)
