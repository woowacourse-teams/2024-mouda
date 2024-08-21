import { PropsWithChildren } from 'react';
import FunnelQuestionHighlight from './FunnelQuestionHighlight/FunnelQuestionHighlight';
import FunnelTextQuestionText from './FunnelQuestionText/FunnelQuestionText';

function FunnelQuestion(props: PropsWithChildren) {
  const { children } = props;

  return <h5>{children}</h5>;
}

FunnelQuestion.Text = FunnelTextQuestionText;
FunnelQuestion.Highlight = FunnelQuestionHighlight;

export default FunnelQuestion;
