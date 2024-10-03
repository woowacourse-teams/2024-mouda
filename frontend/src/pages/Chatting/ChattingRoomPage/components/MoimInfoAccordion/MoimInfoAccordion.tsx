import * as S from './MoimInfoAccordion.styles';

import { MoimStatus } from '@_types/index';
import Tag from '@_pages/Moim/MoimDetailPage/components/Tag/Tag';
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
      <Tag status={status} />
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
