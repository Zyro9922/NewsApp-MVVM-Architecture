# Introducing Dagger into our project

## 1. Identification of Dependencies:
Identified dependencies in the code, such as:
- TopHeadlineRepository depending on NetworkService.
- TopHeadlinesActivity depending on TopHeadlineRepository.

## 2. Dagger Components and Modules Definition:

### ApplicationComponent:
- Created ApplicationComponent, which is a Dagger component with a @Singleton scope.
- Defined the component with modules like NewsApplicationModule.
- Scoped to manage application-level dependencies.

### TopHeadlineActivityComponent:

- Created TopHeadlineActivityComponent, another Dagger component.
- Depended on ApplicationComponent.
- Scoped with @ActivityScope to manage dependencies related to TopHeadlinesActivity.

## 3. Dagger Modules:

### NewsApplicationModule:

- Defined NewsApplicationModule to provide dependencies at the application level.
- Created methods, e.g., provideNetworkService(), for creating instances with a larger lifecycle.

### TopHeadlineActivityModule:

- Defined TopHeadlineActivityModule to provide dependencies specific to TopHeadlinesActivity.

## 4. Dependency Hierarchy:

Established a clear hierarchy of dependencies, where instances like NetworkService are provided by ApplicationComponent with a larger scope, and instances like TopHeadlineRepository are scoped to the activity with TopHeadlineActivityComponent.

## 5. Fine-Grained Control with Scopes:

- Utilized scopes (@Singleton, @ActivityScope) to manage the lifecycle of instances appropriately.




