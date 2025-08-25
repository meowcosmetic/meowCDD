#!/bin/bash

echo "Testing Neon PostgreSQL database connection..."

# Test database connectivity using psql (if available)
if command -v psql &> /dev/null; then
    echo "Testing with psql..."
    PGPASSWORD=npg_BzJ0klf6NIGj psql -h ep-calm-wave-ad7wnkd8-pooler.c-2.us-east-1.aws.neon.tech -U neondb_owner -d "meow's Org" -c "SELECT version();" --set=sslmode=require
else
    echo "psql not available, testing with curl..."
    # Test if the host is reachable
    curl -I https://ep-calm-wave-ad7wnkd8-pooler.c-2.us-east-1.aws.neon.tech:5432 2>/dev/null || echo "Host not reachable via HTTPS"
fi

echo "Database connection test completed."
