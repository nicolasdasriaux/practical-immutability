package practicalimmutability.presentation.adt.visitor;

import practicalimmutability.presentation.adt.matcher.Action.Jump;
import practicalimmutability.presentation.adt.matcher.Action.Sleep;
import practicalimmutability.presentation.adt.matcher.Action.Walk;

public interface ActionVisitor<R> {
    R visitSleep(Sleep sleep);
    R visitWalk(Walk walk);
    R visitJump(Jump jump);
}
