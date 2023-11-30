package com.evstation.charge.validation;

import javax.validation.GroupSequence;

import com.evstation.charge.validation.ValidationGroups.NotEmptyCheckGroup;
import com.evstation.charge.validation.ValidationGroups.PatternCheckGroup;
import com.evstation.charge.validation.ValidationGroups.SizeCheckGroup;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@GroupSequence({Default.class, NotEmptyCheckGroup.class,PatternCheckGroup.class, SizeCheckGroup.class})
public interface ValidationSequence {

}
