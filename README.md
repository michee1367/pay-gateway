
# Compiler tous les modules
mvn compile -B

# run 
pm2 start ecosystem.config.js

# send
```bash
        String phoneNumber,
        BigDecimal amount,
        String currency,
        String reference,
        String action, // "debit" (Retrait) ou "credit" (Dépôt)
        String provider, // "mpesa", "orange", "airtel"
        @JsonProperty("callback_url") String callbackUrl

curl -X POST https://paygateway.mink67.com/api/v1/gateway/request \
  -H "Content-Type: application/json" \
  -d '{
    "phone_number": "0824019836",
    "amount": "1500",
    "currency": "CDF",
    "action": "0824019836",
    "reference": "Test",
    "provider": "mpesa",
    "callback_url": "https://payment.afidingcapital.com/mobile-money/internal/mobile-money/fresh-pay-callback"
  }'
  
curl -X POST http://localhost:8082/api/v1/gateway/request \
  -H "Content-Type: application/json" \
  -d '{
    "phone_number": "0824019836",
    "amount": "1500",
    "currency": "CDF",
    "action": "0824019836",
    "reference": "Test",
    "provider": "mpesa",
    "callback_url": "https://payment.afidingcapital.com/mobile-money/internal/mobile-money/fresh-pay-callback"
  }'

```
