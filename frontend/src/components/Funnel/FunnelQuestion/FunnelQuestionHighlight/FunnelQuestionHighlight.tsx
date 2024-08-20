import { useTheme } from '@emotion/react';
import { PropsWithChildren } from 'react';
import * as S from './FunnelQuestionHighlight.style';

export default function FunnelQuestionHighlight(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <span css={[theme.typography.h5, S.text({ theme })]}>{children}</span>;
}
