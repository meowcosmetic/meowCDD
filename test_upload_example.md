# Test File Upload Example

## 1. Test Upload Book with File (PowerShell)

```powershell
# Create a test PDF file first
echo "This is a test PDF content" > test-book.pdf

# Upload book with file
curl -X POST "http://localhost:8101/api/v1/neon/books/upload" `
  -H "Content-Type: multipart/form-data" `
  -F "title=Test Book" `
  -F "author=Test Author" `
  -F "description=This is a test book for file upload" `
  -F "isbn=978-123-456-789-0" `
  -F "publicationYear=2024" `
  -F "pageCount=50" `
  -F "language=en" `
  -F "ageGroup=PRESCHOOL" `
  -F "supportedFormatId=1" `
  -F "developmentalDomainIds=550e8400-e29b-41d4-a716-446655440001" `
  -F "contentUploadedBy=test-user" `
  -F "file=@test-book.pdf"
```

## 2. Test Upload Book with File (Bash)

```bash
# Create a test PDF file first
echo "This is a test PDF content" > test-book.pdf

# Upload book with file
curl -X POST "http://localhost:8101/api/v1/neon/books/upload" \
  -H "Content-Type: multipart/form-data" \
  -F "title=Test Book" \
  -F "author=Test Author" \
  -F "description=This is a test book for file upload" \
  -F "isbn=978-123-456-789-0" \
  -F "publicationYear=2024" \
  -F "pageCount=50" \
  -F "language=en" `
  -F "ageGroup=PRESCHOOL" `
  -F "supportedFormatId=1" `
  -F "developmentalDomainIds=550e8400-e29b-41d4-a716-446655440001" `
  -F "contentUploadedBy=test-user" `
  -F "file=@test-book.pdf"
```

## 3. Test Download File

```bash
# Download the uploaded file (replace {book_id} with actual ID)
curl -X GET "http://localhost:8101/api/v1/neon/books/{book_id}/content-file" \
  -H "Accept: application/octet-stream" \
  --output "downloaded-test-book.pdf"
```

## 4. Test Get Books with Content Files

```bash
# Get all books with content files
curl -X GET "http://localhost:8101/api/v1/neon/books/with-content-files"
```

## 5. Test Content File Statistics

```bash
# Get content file statistics
curl -X GET "http://localhost:8101/api/v1/neon/books/content-file-statistics"
```

## Expected Results

1. **Upload Success**: Should return 201 Created with book details including file metadata
2. **File Size**: Should be less than 10MB
3. **File Type**: Should be auto-detected (PDF in this case)
4. **Download**: Should be able to download the file with original name
5. **Statistics**: Should show file count and size information

## Troubleshooting

If you get errors:

1. **"Maximum upload size exceeded"**: File is larger than 10MB
2. **"column content_file is of type bytea but expression is of type bigint"**: Database migration not run properly
3. **"Book not found"**: Check if the book ID exists
4. **"Supported format not found"**: Make sure supported format with ID 1 exists
5. **"Developmental domain not found"**: Make sure developmental domain with the UUID exists
