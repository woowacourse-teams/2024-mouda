import * as S from './MoimInfoAccordion.styles';

import { MoimStatus } from '@_types/index';
import { formatHhmmToKoreanWithPrefix } from '@_utils/formatters';
import { useTheme } from '@emotion/react';

interface MoimInfoAccordionProps {
  date?: string;
  time?: string;
  place?: string;
  status: MoimStatus;
}

export default function MoimInfoAccordion(props: MoimInfoAccordionProps) {
  const { date, time, place, status } = props;
  const theme = useTheme();
  return (
    <section css={S.accordion}>
      <div css={S.tag({ theme, isStarted: status === 'COMPLETED' })}>
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
      </div>
    </section>
  );
}
