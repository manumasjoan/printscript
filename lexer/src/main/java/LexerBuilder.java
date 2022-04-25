import java.util.List;

public class LexerBuilder {

    public Lexer buildLexer1(){
        AssignationTokenGenerator assignationTokenGenerator = new AssignationTokenGenerator(List.of("="));
        KeywordTokenGenerator keywordTokenGenerator = new KeywordTokenGenerator(List.of("let", "println"));
        OperatorTokenGenerator operatorTokenGenerator = new OperatorTokenGenerator(List.of("+", "-", "*", "/"));
        SeparatorTokenGenerator separatorTokenGenerator = new SeparatorTokenGenerator(List.of("(", ")",";", ":"));
        IdentifierTokenGenerator identifierTokenGenerator = new IdentifierTokenGenerator();
        NumberTokenGenerator numberTokenGenerator = new NumberTokenGenerator();
        SkipLineTokenGenerator skipLineTokenGenerator = new SkipLineTokenGenerator();
        SpaceTokenGenerator spaceTokenGenerator = new SpaceTokenGenerator();
        StringTokenGenerator stringTokenGenerator = new StringTokenGenerator();

        List<TokenGenerator> tokenGenerators = List.of(stringTokenGenerator,numberTokenGenerator,spaceTokenGenerator,skipLineTokenGenerator,keywordTokenGenerator,operatorTokenGenerator,assignationTokenGenerator,identifierTokenGenerator,separatorTokenGenerator);

        return new DefaultLexer(tokenGenerators);

    }

    public Lexer buildLexer2(){
        AssignationTokenGenerator assignationTokenGenerator = new AssignationTokenGenerator(List.of("="));
        KeywordTokenGenerator keywordTokenGenerator = new KeywordTokenGenerator(List.of("let", "println","const","if","else","readInput"));
        OperatorTokenGenerator operatorTokenGenerator = new OperatorTokenGenerator(List.of("+", "-", "*", "/"));
        SeparatorTokenGenerator separatorTokenGenerator = new SeparatorTokenGenerator(List.of("(", ")","{", "}",";", ":"));
        IdentifierTokenGenerator identifierTokenGenerator = new IdentifierTokenGenerator();
        NumberTokenGenerator numberTokenGenerator = new NumberTokenGenerator();
        SkipLineTokenGenerator skipLineTokenGenerator = new SkipLineTokenGenerator();
        SpaceTokenGenerator spaceTokenGenerator = new SpaceTokenGenerator();
        StringTokenGenerator stringTokenGenerator = new StringTokenGenerator();

        List<TokenGenerator> tokenGenerators = List.of(stringTokenGenerator,numberTokenGenerator,spaceTokenGenerator,skipLineTokenGenerator,keywordTokenGenerator,operatorTokenGenerator,assignationTokenGenerator,identifierTokenGenerator,separatorTokenGenerator);

        return new DefaultLexer(tokenGenerators);

    }
}
