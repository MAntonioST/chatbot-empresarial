#!/bin/bash

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

BASE_URL="http://localhost:8090/api/v1/chat"

echo -e "${BLUE}🧪 Testando Chatbot Empresarial com Groq${NC}\n"

# Teste 1: Health Check
echo -e "${YELLOW}1️⃣  Health Check...${NC}"
HEALTH=$(curl -s $BASE_URL/health)
if [[ $HEALTH == *"funcionando"* ]]; then
    echo -e "${GREEN}✅ $HEALTH${NC}\n"
else
    echo -e "${RED}❌ Falhou: $HEALTH${NC}\n"
    exit 1
fi

# Teste 2: Pergunta sobre Java
echo -e "${YELLOW}2️⃣  Pergunta: O que é Java 21?${NC}"
RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{"message": "Explique o que é Java 21 em 2 linhas"}')

if [[ $RESPONSE == *"response"* ]]; then
    echo -e "${GREEN}✅ Resposta recebida:${NC}"
    echo "$RESPONSE" | jq -r '.response' | fold -w 80 -s
    echo ""
else
    echo -e "${RED}❌ Erro: $RESPONSE${NC}\n"
    exit 1
fi

# Teste 3: Pergunta sobre Spring Boot
echo -e "${YELLOW}3️⃣  Pergunta: Vantagens do Spring Boot?${NC}"
RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{"message": "Liste 3 vantagens do Spring Boot"}')

if [[ $RESPONSE == *"response"* ]]; then
    echo -e "${GREEN}✅ Resposta recebida:${NC}"
    echo "$RESPONSE" | jq -r '.response' | fold -w 80 -s
    echo ""
else
    echo -e "${RED}❌ Erro: $RESPONSE${NC}\n"
    exit 1
fi

# Teste 4: Pergunta sobre Microserviços
echo -e "${YELLOW}4️⃣  Pergunta: O que são microserviços?${NC}"
RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{"message": "Explique microserviços em 3 linhas"}')

if [[ $RESPONSE == *"response"* ]]; then
    echo -e "${GREEN}✅ Resposta recebida:${NC}"
    echo "$RESPONSE" | jq -r '.response' | fold -w 80 -s
    echo ""
else
    echo -e "${RED}❌ Erro: $RESPONSE${NC}\n"
    exit 1
fi

echo -e "${GREEN}🎉 Todos os testes passaram!${NC}"
