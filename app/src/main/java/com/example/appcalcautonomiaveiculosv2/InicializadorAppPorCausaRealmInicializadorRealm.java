package com.example.appcalcautonomiaveiculosv2;

import android.app.Application;

import com.example.appcalcautonomiaveiculosv2.Modelo.GerenciadorMigrationsRealm;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class InicializadorAppPorCausaRealmInicializadorRealm extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        // Migrations

        // Essas 3 linhas de código abaixo representam a 1a parte relacionada a todas as mudanças necessárias q devem ser feitas no bd p/ q os usus antigos possam usar o app na nova versão sem perder todos os seus dados gravados (mecanismo de versionamento da estrutura dos dados (Estrutura dos dados = Schema)).
        // Nessas 3 linhas o troço é basicamente:
        //      - Construir uma configuração do Realm e dizer q o bd está na versão 1 atualmente (versão 0 é a inicial);
        //      - Setar a configuração padrão do Realm c/ essa configuração q foi criada acima;
        //      - Iniciar o singleton do Realm passando a configuração q criamos. * C/ relação a esse singleton do Realm, toda vez q se acessa o Realm do nosso app, é acessado o msm obj.
        RealmConfiguration configuracaoRealm = new RealmConfiguration.Builder().schemaVersion(1).migration(new GerenciadorMigrationsRealm()).build();
        Realm.setDefaultConfiguration( configuracaoRealm );
        Realm.getInstance( configuracaoRealm );
    }
}
