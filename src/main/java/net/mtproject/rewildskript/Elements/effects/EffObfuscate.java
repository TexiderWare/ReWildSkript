package net.mtproject.rewildskript.Elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.mtproject.rewildskript.Util.Obfuscator;
import org.bukkit.event.Event;

public class EffObfuscate extends Effect {

    static {
        Skript.registerEffect(EffObfuscate.class, "obfuscate file %string% to file %string% with power %number%");
    }

    private Expression<String> from;
    private Expression<String> to;
    private Expression<Number> power;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.from = (Expression<String>) expressions[0];
        this.to = (Expression<String>) expressions[1];
        this.power = (Expression<Number>) expressions[2];
        return true;
    }

    @Override
    protected void execute(Event event) {
        String sourceFilePath = from.getSingle(event);
        String destinationFilePath = to.getSingle(event);
        Number powerValue = power.getSingle(event);
        if (sourceFilePath == null || destinationFilePath == null || powerValue == null) {
            return;
        }
        try {
            Obfuscator.obfuscate(sourceFilePath, destinationFilePath, powerValue.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "EffObfuscate class: obfuscating file with specified power";
    }
}
