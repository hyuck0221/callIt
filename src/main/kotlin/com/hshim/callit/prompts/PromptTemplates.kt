package com.hshim.callit.prompts

object PromptTemplates {

    fun buildNamingPrompt(
        code: String,
        language: String,
        maxSuggestions: Int
    ): String {
        val namingConventions = getNamingConventions(language)

        return """
        You are an expert code naming assistant. Analyze the following code and suggest appropriate variable or function names.
        
        **Code to analyze:**
        ```
        $code
        ```
        
        **Programming Language:** $language
        
        **Naming Conventions for $language:**
        $namingConventions
        
        **Instructions:**
        1. Analyze the code's purpose, functionality, and context
        2. Consider the programming language's naming conventions
        3. Generate $maxSuggestions distinct, descriptive, and appropriate names
        4. Each name should be:
           - Clear and self-documenting
           - Follow the language's standard naming conventions
           - Reflect the code's purpose or functionality
           - Avoid abbreviations unless widely accepted
           - Be consistent with common patterns in $language
        
        **Output Format:**
        Provide exactly $maxSuggestions name suggestions, one per line, without numbering or additional explanation.
        Just the names, separated by newlines.
        
        Example output format:
        getUserProfile
        fetchUserData
        retrieveUserInformation
""".trimIndent()
    }

    private fun getNamingConventions(language: String): String {
        return when (language.lowercase()) {
            "java", "kotlin" -> """
            - Classes: PascalCase (e.g., UserProfile, CustomerService)
            - Variables/Functions: camelCase (e.g., getUserData, customerAge)
            - Constants: UPPER_SNAKE_CASE (e.g., MAX_RETRY_COUNT, API_BASE_URL)
            - Packages: lowercase (e.g., com.example.service)
            - Interfaces: PascalCase, often with 'I' prefix or descriptive adjectives (e.g., Serializable, Runnable)
            - Boolean variables: start with is/has/can/should (e.g., isActive, hasPermission)
            """.trimIndent()

            "javascript", "typescript" -> """
            - Classes: PascalCase (e.g., UserProfile, DataProcessor)
            - Variables/Functions: camelCase (e.g., getUserData, itemCount)
            - Constants: UPPER_SNAKE_CASE or camelCase (e.g., MAX_ITEMS, apiKey)
            - Components (React): PascalCase (e.g., UserCard, NavigationBar)
            - Boolean variables: start with is/has/can/should (e.g., isLoading, hasError)
            - Private members: prefix with _ or # (e.g., _privateMethod, #privateField)
            """.trimIndent()

            "python" -> """
            - Classes: PascalCase (e.g., UserProfile, DataProcessor)
            - Variables/Functions: snake_case (e.g., get_user_data, item_count)
            - Constants: UPPER_SNAKE_CASE (e.g., MAX_RETRY_COUNT, API_BASE_URL)
            - Modules: lowercase with underscores (e.g., user_service, data_utils)
            - Private members: prefix with _ or __ (e.g., _private_method, __very_private)
            - Boolean variables: use descriptive names (e.g., is_active, has_permission)
            """.trimIndent()

            "go" -> """
            - Types/Structs: PascalCase (e.g., UserProfile, HTTPClient)
            - Functions: PascalCase for exported, camelCase for unexported (e.g., GetUser, parseData)
            - Variables: camelCase (e.g., userName, itemCount)
            - Constants: PascalCase or camelCase (e.g., MaxRetryCount, apiTimeout)
            - Interfaces: PascalCase, often ending with 'er' (e.g., Reader, Writer, UserFetcher)
            - Acronyms: all caps if short (HTTP, ID), only first letter capitalized if long (Html, Xml)
            """.trimIndent()

            "rust" -> """
            - Types/Structs/Enums: PascalCase (e.g., UserProfile, HttpClient)
            - Functions/Variables: snake_case (e.g., get_user_data, item_count)
            - Constants: UPPER_SNAKE_CASE (e.g., MAX_RETRY_COUNT, API_BASE_URL)
            - Traits: PascalCase, often descriptive adjectives (e.g., Clone, Display, Serialize)
            - Modules: snake_case (e.g., user_service, data_utils)
            - Lifetimes: single lowercase letter with apostrophe (e.g., 'a, 'static)
            """.trimIndent()

            "c#", "csharp" -> """
            - Classes/Interfaces: PascalCase (e.g., UserProfile, IUserService)
            - Methods/Properties: PascalCase (e.g., GetUserData, ItemCount)
            - Variables (local): camelCase (e.g., userName, itemCount)
            - Constants: PascalCase or UPPER_SNAKE_CASE (e.g., MaxRetryCount, API_BASE_URL)
            - Private fields: camelCase with _ prefix (e.g., _userName, _itemCount)
            - Interfaces: PascalCase with 'I' prefix (e.g., IDisposable, IUserRepository)
            """.trimIndent()

            "swift" -> """
            - Classes/Structs/Protocols: PascalCase (e.g., UserProfile, Codable)
            - Functions/Variables: camelCase (e.g., getUserData, itemCount)
            - Constants: camelCase or UPPER_SNAKE_CASE (e.g., maxRetryCount, API_BASE_URL)
            - Enum cases: camelCase (e.g., .loading, .success, .failure)
            - Boolean variables: use descriptive names (e.g., isActive, hasPermission)
            """.trimIndent()

            "ruby" -> """
            - Classes/Modules: PascalCase (e.g., UserProfile, DataProcessor)
            - Methods/Variables: snake_case (e.g., get_user_data, item_count)
            - Constants: UPPER_SNAKE_CASE (e.g., MAX_RETRY_COUNT, API_BASE_URL)
            - Predicates: end with ? (e.g., empty?, valid?, active?)
            - Dangerous methods: end with ! (e.g., save!, destroy!)
            - Instance variables: prefix with @ (e.g., @user_name, @item_count)
            - Class variables: prefix with @@ (e.g., @@instance_count)
            """.trimIndent()

            "php" -> """
            - Classes: PascalCase (e.g., UserProfile, DataProcessor)
            - Functions/Methods: camelCase (e.g., getUserData, processItem)
            - Variables: camelCase with $ prefix (e.g., $\userName, $\itemCount)
            - Constants: UPPER_SNAKE_CASE (e.g., MAX_RETRY_COUNT, API_BASE_URL)
            - Namespaces: PascalCase (e.g., App\Services\UserService)
            - Interfaces: PascalCase, often with 'Interface' suffix (e.g., UserServiceInterface)
            """.trimIndent()

            else -> """
            - Use meaningful, descriptive names
            - Be consistent with the language's common conventions
            - Avoid single-letter names except for loop counters
            - Use clear, self-documenting names
            - Follow the most common patterns in the programming community
            """.trimIndent()
        }
    }
}
