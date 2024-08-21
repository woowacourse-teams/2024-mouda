import { useTheme } from '@emotion/react';
import { PropsWithChildren } from 'react';

export default function FunnelTextQuestionText(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <span css={theme.typography.h5}>{children}</span>;
}
