package net.mtproject.rewildskript.Elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.mtproject.rewildskript.Util.Loader;
import net.mtproject.rewildskript.Util.Obfuscator;
import org.bukkit.event.Event;

import java.io.File;
import java.util.regex.Matcher;

public class EffLoad extends Effect {

    static {
        Skript.registerEffect(EffLoad.class, "load obfuscated file %string% by power %number%");
    }

    private Expression<String> from;
    private Expression<Number> power;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.from = (Expression<String>) expressions[0];
        this.power = (Expression<Number>) expressions[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        String filePath = from.getSingle(event);
        Number powerValue = power.getSingle(event);
        if (filePath == null || powerValue == null) {
            return;
        }
        try {
            String code = Obfuscator.deobfuscate(new File(filePath.replaceAll("/", Matcher.quoteReplacement(File.separator))), powerValue.intValue());
            Loader.loadString(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "EffLoad class: loading obfuscated file with specified power";
    }
}
