#!/bin/bash

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${YELLOW}🔍 Validando API Key do Groq...${NC}\n"

# Carregar .env
if [ ! -f .env ]; then
    echo -e "${RED}❌ Arquivo .env não encontrado!${NC}"
    exit 1
fi

export $(cat .env | xargs)

if [ -z "$GROQ_API_KEY" ]; then
    echo -e "${RED}❌ GROQ_API_KEY não encontrada no .env${NC}"
    exit 1
fi

echo -e "📝 API Key: ${GROQ_API_KEY:0:15}...\n"

# Testar API
echo -e "${YELLOW}🧪 Testando conexão com Groq...${NC}\n"

RESPONSE=$(curl -s https://api.groq.com/openai/v1/models \
  -H "Authorization: Bearer $GROQ_API_KEY")

if echo "$RESPONSE" | grep -q '"object":"list"'; then
    echo -e "${GREEN}✅ API Key válida!${NC}"
    echo -e "${GREEN}✅ Conexão com Groq estabelecida${NC}\n"
    
    echo -e "${YELLOW}📋 Modelos disponíveis:${NC}"
    echo "$RESPONSE" | grep -o '"id":"[^"]*"' | sed 's/"id":"/  ✓ /' | sed 's/"$//'
    
    echo -e "\n${GREEN}🎉 Tudo pronto para usar!${NC}"
    exit 0
else
    echo -e "${RED}❌ Erro ao conectar com Groq${NC}\n"
    echo -e "${YELLOW}Resposta:${NC}"
    echo "$RESPONSE" | head -10
    
    echo -e "\n${YELLOW}📝 Verifique:${NC}"
    echo "1. API Key está correta"
    echo "2. Acesse: https://console.groq.com/keys"
    exit 1
fi
