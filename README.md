
# Compiler tous les modules
mvn compile -B

# export 
export GATEWAY_SECRET='##########################'

# Wallet service (profil dev = auth mock + pas de Keycloak requis)
mvn spring-boot:run
