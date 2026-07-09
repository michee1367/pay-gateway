module.exports = {
  apps: [{
    name: "pay-gateway",
    script: "mvn",
    args: "spring-boot:run",
    exec_mode: "fork",        // ◄--- Crucial : dit à PM2 de ne pas traiter mvn comme du JS
    interpreter: "bash",      // ◄--- Crucial : force l'utilisation de Bash pour exécuter Maven
    env: {
      GATEWAY_SECRET: "jzbmPKd!6CldzCMQ@b" // Ici, aucune interprétation possible !
    }
  }]
}