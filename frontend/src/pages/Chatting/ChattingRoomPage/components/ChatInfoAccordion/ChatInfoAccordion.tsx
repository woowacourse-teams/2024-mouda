import * as S from './ChatInfoAccordion.styles';

import { useTheme } from '@emotion/react';

interface MoimInfoAccordionProps {
  tagValue: string;
  textList: string[];
  tagTheme: 'yellow' | 'orange';
}

export default function ChatInfoAccordion(props: MoimInfoAccordionProps) {
  const { tagTheme, textList, tagValue } = props;
  const theme = useTheme();
  return (
    <section css={S.accordion}>
      <div css={S.tag({ theme, tagTheme })}>{tagValue}</div>
      <div
        css={[
          S.textArea,
          theme.coloredTypography.Medium(theme.colorPalette.grey[500]),
        ]}
      >
        {textList.some((text) => text.trim().length > 0) ? (
          textList.map((text) => {
            const targetText = text.trim();
            if (!targetText) return null;
            return (
              <div
                key={text}
                css={theme.coloredTypography.Medium(
                  theme.colorPalette.grey[500],
                )}
              >
                {text}
              </div>
            );
          })
        ) : (
          <div
            css={theme.coloredTypography.Medium(theme.colorPalette.grey[500])}
          >
            {' '}
            아직 정한 모임 정보가 없습니다
          </div>
        )}
      </div>
    </section>
  );
}

{
  /* <div css={S.tag({ theme, isStarted: status === 'COMPLETED' })}>
{status === 'COMPLETED' ? '모임 후' : '모임 전'}
</div>
<div
css={[
  S.textArea,
  theme.coloredTypography.Medium(theme.colorPalette.grey[500]),
]}
>
<div
  css={[theme.coloredTypography.Medium(theme.colorPalette.grey[500])]}
>
  {date && date.replaceAll('-', '.')}
  {time && ' ' + formatHhmmToKoreanWithPrefix(time)}
</div>
{place && place}
{!date && !time && !place && '아직 정한 모임 정보가 없습니다'}
</div> */
}
