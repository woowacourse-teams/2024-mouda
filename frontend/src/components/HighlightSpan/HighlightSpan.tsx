import * as S from './HighlightSpan.style';

import { PropsWithChildren, createContext, useContext } from 'react';
import { SerializedStyles, useTheme } from '@emotion/react';

interface HighlightSpanProps extends PropsWithChildren {
  highlightColor?: SerializedStyles | string;
  normalColor?: SerializedStyles | string;
  font?: SerializedStyles | string;
}

interface HighlightSpanContext {
  highlightColor: SerializedStyles | string;
  normalColor: SerializedStyles | string;
  font: SerializedStyles | string;
}
const HighlightSpanContext = createContext<HighlightSpanContext | null>(null);

function HighlightSpan(props: HighlightSpanProps) {
  const theme = useTheme();
  const {
    highlightColor = theme.semantic.primary,
    normalColor = theme.colorPalette.black[100],
    font = theme.typography.h5,
    children,
  } = props;

  return (
    <HighlightSpanContext.Provider
      value={{ highlightColor, normalColor, font }}
    >
      <span css={S.text({ color: normalColor, font })}>{children}</span>
    </HighlightSpanContext.Provider>
  );
}

HighlightSpan.Highlight = function Highlight(props: PropsWithChildren) {
  const { children } = props;
  const context = useContext(HighlightSpanContext);
  if (!context) {
    throw new Error('HighlightSpan context가 없습니다');
  }
  return (
    <span css={[S.text({ color: context.highlightColor, font: context.font })]}>
      {children}
    </span>
  );
};

export default HighlightSpan;
