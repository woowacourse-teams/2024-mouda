import BackArrowLogo from './BackArrowLogo';
import * as S from './MoimRegister.style';

export default function MoimRegisterLayout() {
  return (
    <div css={S.containerStyle}>
      {/* TODO: '모임등록하기'가 정가운데에 오지 않음 */}
      <header css={S.headerStyle}>
        <span>
          <BackArrowLogo />
        </span>
        <span css={S.headerTitleStyle}>모임등록하기</span>
        <span></span>
      </header>

      <form css={S.formStyle}>
        {/* <label css={S.labelStyle}>
          제목 <span css={S.requiredStyle}>*</span>
          <input
            type="text"
            placeholder="모임의 제목을 입력해주세요."
            css={S.inputStyle}
            required
          />
        </label>
        <label css={S.labelStyle}>
          날짜 <span css={S.requiredStyle}>*</span>
          <input type="date" css={S.inputStyle} required />
        </label>
        <label css={S.labelStyle}>
          시간 <span css={S.requiredStyle}>*</span>
          <input type="time" css={S.inputStyle} required />
        </label>
        <label css={S.labelStyle}>
          장소 <span css={S.requiredStyle}>*</span>
          <input type="text" placeholder="없음" css={S.inputStyle} required />
        </label>
        <label css={S.labelStyle}>
          최대인원수 <span css={S.requiredStyle}>*</span>
          <input
            type="number"
            min="1"
            placeholder="0명"
            css={S.inputStyle}
            required
          />
        </label>
        <label css={S.labelStyle}>
          작성자 닉네임 <span css={S.requiredStyle}>*</span>
          <input
            type="text"
            placeholder="알았다 안냐야~"
            css={S.inputStyle}
            required
          />
        </label>
        <label css={S.labelStyle}>
          작성자 닉네임 <span css={S.requiredStyle}>*</span>
          <input
            type="text"
            placeholder="알았다 안냐야~"
            css={S.inputStyle}
            required
          />
        </label>
        <label css={S.labelStyle}>
          작성자 닉네임 <span css={S.requiredStyle}>*</span>
          <input
            type="text"
            placeholder="알았다 안냐야~"
            css={S.inputStyle}
            required
          />
        </label>
        <label css={S.labelStyle}>
          작성자 닉네임 <span css={S.requiredStyle}>*</span>
          <input
            type="text"
            placeholder="알았다 안냐야~"
            css={S.inputStyle}
            required
          />
        </label>
        <label css={S.labelStyle}>
          작성자 닉네임 <span css={S.requiredStyle}>*</span>
          <input
            type="text"
            placeholder="알았다 안냐야~"
            css={S.inputStyle}
            required
          />
        </label>
        <label css={S.labelStyle}>
          상세 내용 작성
          <textarea
            placeholder="어떤 모임인지 작성해주세요!"
            css={S.textareaStyle}
          ></textarea>
        </label> */}
      </form>
      <div css={S.bottomFixedStyle}>
        <button type="submit" css={S.buttonStyle}>
          등록하기
        </button>
      </div>
    </div>
  );
}
