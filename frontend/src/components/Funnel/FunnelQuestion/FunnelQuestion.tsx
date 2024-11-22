import { LabelHTMLAttributes, PropsWithChildren } from 'react';
import FunnelQuestionHighlight from './FunnelQuestionHighlight/FunnelQuestionHighlight';
import FunnelTextQuestionText from './FunnelQuestionText/FunnelQuestionText';

interface FunnelQuestionProps extends LabelHTMLAttributes<HTMLLabelElement> {}

function FunnelQuestion(props: PropsWithChildren<FunnelQuestionProps>) {
  const { children, ...rest } = props;

  return <label {...rest}>{children}</label>;
}

FunnelQuestion.Text = FunnelTextQuestionText;
FunnelQuestion.Highlight = FunnelQuestionHighlight;

export default FunnelQuestion;
