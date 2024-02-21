# mini_Framework_injection_dependencies

Ce projet consiste en un mini-framework d'injection de dépendances en Java, inspiré par Spring IOC, qui permet aux développeurs de gérer les dépendances entre les composants de leurs applications de manière flexible et efficace.

## Fonctionnalités

Le mini-framework offre les fonctionnalités suivantes :

  Injection via Annotations : Les développeurs peuvent marquer les champs, constructeurs ou méthodes avec l'annotation @Inject pour spécifier les dépendances à injecter.
  Support pour différents points d'injection : Les dépendances peuvent être injectées via le constructeur, les méthodes ou les champs, selon les besoins de l'application.
  Configuration via XML : (À ajouter dans les futures versions) : Le framework prendra également en charge la configuration des dépendances via des fichiers XML à l'aide de JAX Binding, offrant ainsi une alternative à l'injection par annotations.
  
## Utilisation
Voici un exemple d'utilisation du framework :
`// Marquer les dépendances à injecter avec @Inject
class MyClass {
    @Inject
    private MyDependency dependency;

    @Inject
    public MyClass(MyDependency dependency) {
        this.dependency = dependency;
    }
    
    @Inject
    public void setDependency(MyDependency dependency) {
        this.dependency = dependency;
    }
}

// Configuration et utilisation du conteneur IoC
public class MyApp {
    public static void main(String[] args) throws Exception {
        IoCContainer container = new IoCContainer();  
        // Ajouter les dépendances au conteneur IoC
        container.addObject(MyDependency.class, new MyDependencyImpl());

        // Obtenir les instances avec les dépendances injectées
        MyClass myInstance = container.getObject(MyClass.class);
        myInstance.doSomething();
    }
}
`
## Contribution
Les contributions sont les bienvenues ! Si vous souhaitez contribuer à ce projet, n'hésitez pas à ouvrir une issue ou à proposer une pull request.
