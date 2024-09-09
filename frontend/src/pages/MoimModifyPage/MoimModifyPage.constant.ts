import POLICES from '@_constants/poclies';
import {
  validateDate,
  validateMaxPeople,
  validatePlace,
  validateTime,
  validateTitle,
} from './MoimModifyPage.util';
import { MoimInputInfo } from '../../types';

interface LabeledInputInfo {
  name: keyof MoimInputInfo;
  title: string;
  type: string;
  placeholder: string;
  required: boolean;
  validate?: (value: string) => boolean;
}

const MOIM_INPUT_INFOS: LabeledInputInfo[] = [
  {
    name: 'title',
    title: '제목',
    type: 'text',
    placeholder: `${POLICES.minimumTitleLength}자 이상 ${POLICES.maximumTitleLength}자 이하로 입력해주세요`,
    required: true,
    validate: validateTitle, // string 타입
  },
  {
    name: 'date',
    title: '날짜',
    type: 'date',
    placeholder: '현재 시간 이후로 설정해주세요',
    required: false,
    validate: validateDate, // string 타입
  },
  {
    name: 'time',
    title: '시간',
    type: 'time',
    placeholder: '현재 시간 이후로 설정해주세요',
    required: false,
    validate: validateTime, // string 타입
  },
  {
    name: 'place',
    title: '장소',
    type: 'text',
    placeholder: `${POLICES.minimumPlaceLength}자 이상 ${POLICES.maximumPlaceLength}자 이하로 입력해주세요`,
    required: false,
    validate: validatePlace,
  },
  {
    name: 'maxPeople',
    title: '최대인원수',
    type: 'number',
    placeholder: '1명 이상 99명 이하로 입력해주세요',
    required: true,
    validate: validateMaxPeople,
  },
  {
    name: 'description',
    title: '상세 내용 작성',
    type: 'textarea',
    placeholder: '어떤 모임인지 작성해주세요!',
    required: false,
  },
] as const;

export default MOIM_INPUT_INFOS;
