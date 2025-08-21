# Orders Service — REST + GraphQL + Kafka + Elasticsearch


## Run locally
```bash
docker compose up -d --build
# REST
curl -X POST http://localhost:8080/api/orders \
-H 'content-type: application/json' \
-d '{"customerEmail":"demo@example.com","amount": 12.34}'


# GraphQL (via curl)
curl -X POST http://localhost:8080/graphql \
-H 'content-type: application/json' \
-d '{"query":"mutation($in:CreateOrderInput!){ createOrder(input:$in){ id status } }",
"variables":{"in":{"customerEmail":"demo@example.com","amount": 42.0}}