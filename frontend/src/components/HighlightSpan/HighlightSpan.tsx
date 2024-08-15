import * as S from './HighlightSpan.style';

import { SerializedStyles, useTheme } from '@emotion/react';

import { useMemo } from 'react';

interface HighlightSpanProps {
  highlightTexts: string[];
  normalTexts: string[];
  isFirstHighlight?: boolean;
  highlightColor?: string;
  normalColor?: string;
  font?: SerializedStyles;
}

export default function HighlightSpan(props: HighlightSpanProps) {
  const theme = useTheme();
  const {
    highlightTexts,
    normalTexts,
    isFirstHighlight = false,
    highlightColor = theme.semantic.primary,
    normalColor = theme.colorPalette.black[100],
    font = theme.typography.h5,
  } = props;

  const elements = useMemo(() => {
    let highlightTextIndex = 0;
    let normalTextIndex = 0;

    const result = [];
    while (
      highlightTextIndex < highlightTexts.length ||
      normalTextIndex < normalTexts.length
    ) {
      const nowHighlightText = highlightTexts[highlightTextIndex++];
      const nowNormalText = normalTexts[normalTextIndex++];
      if (!isFirstHighlight) {
        nowNormalText &&
          result.push(
            <span
              key={normalTextIndex + nowNormalText}
              css={S.text({ color: normalColor, font })}
            >
              {nowNormalText}
            </span>,
          );

        nowHighlightText &&
          result.push(
            <strong
              key={highlightTextIndex + nowHighlightText}
              css={S.text({ color: highlightColor, font })}
            >
              {nowHighlightText}
            </strong>,
          );
      }
      if (isFirstHighlight) {
        nowHighlightText &&
          result.push(
            <strong
              key={highlightTextIndex + nowHighlightText}
              css={S.text({ color: highlightColor, font })}
            >
              {nowHighlightText}
            </strong>,
          );

        nowNormalText &&
          result.push(
            <span
              key={normalTextIndex + nowNormalText}
              css={S.text({ color: normalColor, font })}
            >
              {nowNormalText}
            </span>,
          );
      }
    }
    return result;
  }, [
    font,
    highlightTexts,
    normalTexts,
    highlightColor,
    normalColor,
    isFirstHighlight,
  ]);

  return <span>{elements}</span>;
}
