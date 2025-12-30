#!/bin/bash

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${YELLOW}🔄 Reiniciando com configuração Groq corrigida...${NC}\n"

# Matar processo na porta 8090
if sudo lsof -i :8090 > /dev/null 2>&1; then
    echo -e "${YELLOW}🔪 Matando processo na porta 8090...${NC}"
    sudo fuser -k 8090/tcp
    sleep 2
fi

# Recarregar variáveis
export $(cat .env | xargs)
echo -e "${GREEN}✅ Variáveis carregadas${NC}"

# Limpar e compilar
echo -e "${YELLOW}🔨 Compilando...${NC}"
./mvnw clean compile -q

# Rodar
echo -e "${GREEN}🚀 Iniciando aplicação...${NC}\n"
./mvnw spring-boot:run
