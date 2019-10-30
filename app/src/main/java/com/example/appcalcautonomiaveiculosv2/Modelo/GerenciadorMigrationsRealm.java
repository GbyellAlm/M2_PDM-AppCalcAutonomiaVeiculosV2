package com.example.appcalcautonomiaveiculosv2.Modelo;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

// Esse cara é o cara q vai ser chamado smp q o Schema mudar de versão (2a parte relacionada a todas as mudanças necessárias q devem ser feitas no bd por causa dessa adição da foto).
public class GerenciadorMigrationsRealm implements RealmMigration {
    @Override
    // 1
    public void migrate (DynamicRealm realm, long vVelha, long vNova) {
        // 4 * Faz de 1 em 1 até chegar na versão nova q o cara precisa estar.
        for (long versao = vVelha; versao < vNova; versao++) {
            passoMigration(realm, versao, versao+1);
        }
    }
    // 3
    // Esse "DynamicRealm" representa o bd.
    private void passoMigration(DynamicRealm realm, long versaoVelha, long versaoNova) {
        // 2
        if (versaoVelha == 0 && versaoNova == 1) {
            // * Pega tdas as classes.
            RealmSchema ixquema = realm.getSchema();
            // * Pega a classe DadosAbastecimento.
            RealmObjectSchema dadosAbasSchema = ixquema.get("DadosAbastecimento");
            // * Diz p/ adc um campo no bd chamado caminhoFotografia q vem da classe q foi pega a cima.
            dadosAbasSchema.addField("caminhoFotografia", String.class);
        }
    }
}
